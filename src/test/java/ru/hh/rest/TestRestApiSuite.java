package ru.hh.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by HonneyHelen on 16-Mar-15.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetUserInfo.class,
        PutUserInfo.class
})
public class TestRestApiSuite {
}
