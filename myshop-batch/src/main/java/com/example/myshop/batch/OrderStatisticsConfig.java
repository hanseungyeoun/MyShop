package com.example.myshop.batch;

import com.example.myshop.domain.OrderStatistics;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.repository.OrderStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class OrderStatisticsConfig {


    private final OrderReader orderReader;
    private final OrderStatisticsRepository orderStatisticsRepository;

    @Bean
    public Job orderStatisticsJob(JobRepository jobRepository, Step taskBaseStep) throws Exception {
        return new JobBuilder("orderStatistics", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(taskBaseStep)
                .build();
    }

    @Bean
    @JobScope
    public Step taskBaseStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet orderStatistics) {
        return new StepBuilder("taskBaseStep", jobRepository)
                .tasklet(orderStatistics, transactionManager)
                .build();
    }


    @Bean
    @StepScope
    public Tasklet orderStatistics(@Value("#{jobParameters[date]}") String date) {
        System.out.println("date = " + date);
        return (contribution, chunkContext) -> {
            final LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            final LocalDateTime from =  LocalDateTime.of(localDate, LocalTime.MIN);
            final LocalDateTime to = LocalDateTime.of(localDate, LocalTime.MAX);

            List<Order> orders = orderReader.findByCreatedAtBetween(from, to);
            System.out.println("orders = " + orders);
            Integer calculateTotalAmount = orders.stream()
                    .mapToInt(order->order.getTotalAmount().getMoney().intValue())
                    .sum();

            Integer calculateTotalCount = orders.stream()
                    .mapToInt(Order::calculateTotalCount)
                    .sum();

            OrderStatistics newOrderStatistics = null;
            Optional<OrderStatistics> orderStatistics = orderStatisticsRepository.findByDate(localDate);
            if(orderStatistics.isPresent()){
                newOrderStatistics = orderStatistics.get();
                newOrderStatistics.change(calculateTotalAmount, calculateTotalCount);
            } else {
                newOrderStatistics = new OrderStatistics(calculateTotalAmount, calculateTotalCount, date);
            }
            orderStatisticsRepository.save(newOrderStatistics);
            return RepeatStatus.FINISHED;
        };
    }
}
