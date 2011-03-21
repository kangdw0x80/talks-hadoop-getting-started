package net.joshdevins.talks.hadoopstart.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

public class AccessLogThroughputParseGroupBySecondsMapperTest {

    private static final String TEST_LINE_P1 = "1.2.3.4 - - [30/Sep/2008:15:07:";
    private static final String TEST_LINE_P2 = " -0400] \"GET / HTTP/1.1\" 200 3190 \"-\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4; en-us) AppleWebKit/525.18 (KHTML, like Gecko) Version/3.1.2 Safari/525.20.1\"";

    private AccessLogThroughputParseGroupBySecondsMapper mapper;

    private MapDriver<LongWritable, Text, Text, NullWritable> driver;

    @Before
    public void before() throws Exception {
        mapper = new AccessLogThroughputParseGroupBySecondsMapper();
        driver = new MapDriver<LongWritable, Text, Text, NullWritable>(mapper);
    }

    @Test
    public void test() throws Exception {

        driver.withInput(createTestInput(1, "01"));
        driver.withInput(createTestInput(2, "02"));
        driver.withInput(createTestInput(2, "02"));
        driver.withInput(createTestInput(3, "03"));

        driver.runTest();

        driver.withOutput(new Text("30/Sep/2008:15:07:01 -0400"), NullWritable.get());
        driver.withOutput(new Text("30/Sep/2008:15:07:02 -0400"), NullWritable.get());
        driver.withOutput(new Text("30/Sep/2008:15:07:02 -0400"), NullWritable.get());
        driver.withOutput(new Text("30/Sep/2008:15:07:03 -0400"), NullWritable.get());
    }

    private Pair<LongWritable, Text> createTestInput(final int index, final String string) {
        return new Pair<LongWritable, Text>(new LongWritable(index), new Text(TEST_LINE_P1 + string + TEST_LINE_P2));
    }
}
