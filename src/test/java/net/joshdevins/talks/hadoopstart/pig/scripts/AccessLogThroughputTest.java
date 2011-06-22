package net.joshdevins.talks.hadoopstart.pig.scripts;

import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

public final class AccessLogThroughputTest extends PigUnitBase {

    public AccessLogThroughputTest() {
        super(AccessLogThroughputTest.class);
    }

    @Test
    public void testScript() throws Exception {

        // hack to remove REGISTER command
        String[] args = { "local=--" };
        PigTest test = new PigTest("src/main/pig/access-log-throughput.pig", args);

        String[] input = {
                "1.2.3.4\t-\t-\t30/Sep/2010:15:07:53 -0400\tGET\t/\tHTTP/1.1\t200\t3190\t-\tMozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4; en-us) AppleWebKit/525.18 (KHTML, like Gecko) Version/3.1.2 Safari/525.20.1",
                "1.2.3.4\t-\t-\t30/Sep/2010:15:07:53 -0400\tGET\t/\tHTTP/1.1\t200\t3190\t-\tMozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4; en-us) AppleWebKit/525.18 (KHTML, like Gecko) Version/3.1.2 Safari/525.20.1",
                "1.2.3.4\t-\t-\t30/Sep/2010:15:07:54 -0400\tGET\t/foo\tHTTP/1.1\t200\t3190\t-\t-" };
        String[] output = { "(2010-09-30 15:00:00,3,2,1.5,2.0,2.0,2)" };

        // PigUnit will discard any STORE and DUMP calls
        // PigUnit will replace input tuple with one loaded and parsed with PigStorage
        test.assertOutput("raw", input, "byHour", output);
    }
}
