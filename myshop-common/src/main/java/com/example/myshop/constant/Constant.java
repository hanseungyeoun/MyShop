package com.example.myshop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static final String CATEGORY_PATH_DELIMITER = "/";

    public static final String PROFILE_LOCAL = "local";
    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_PROD = "prod";
    public static final String TEST_TAG_INTEGRATION = "integration";
    public static final String TEST_TAG_DOCS_TEST = "docsTest";

    public static final String HEADER_MEMBER_NUMBER = "X_MEMBER_NUMBER";
}
