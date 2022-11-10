/*
 * @(#)BigDecimalParserJmhBenchmark.java
 * Copyright © 2021. Werner Randelshofer, Switzerland. MIT License.
 */

package ch.randelshofer.fastdoubleparser;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks for selected floating point strings.
 * <pre>
 * # JMH version: 1.28
 * # VM version: OpenJDK 64-Bit Server VM, Oracle Corporation, 19+36-2238
 * # Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz
 *
 *    (digits)  Mode  Cnt   _   _  Score   Error  Units
 * m         1  avgt    2   _   _ 10.454          ns/op
 * m        10  avgt    2   _   _ 16.389          ns/op
 * m       100  avgt    2   _   _ 89.400          ns/op
 * m      1000  avgt    2   _  1_192.894          ns/op
 * m     10000  avgt    2   _ 18_459.700          ns/op
 * m    100000  avgt    2   _176_117.397          ns/op
 * m   1000000  avgt    2  1_578_203.272          ns/op
 * </pre>
 */

@Fork(value = 1, jvmArgsAppend = {
        "-XX:+UnlockExperimentalVMOptions", "--add-modules", "jdk.incubator.vector"
        , "--enable-preview"

        //       ,"-XX:+UnlockDiagnosticVMOptions", "-XX:PrintAssemblyOptions=intel", "-XX:CompileCommand=print,ch/randelshofer/fastdoubleparser/FastDoubleParser.*"

})
@Measurement(iterations = 2)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class JavaDoubleFromByteArrayScalabilityJmh {


    @Param({
            "1",
            "10",
            "100",
            "1000",
            "10000",
            "100000",
            "1000000"
    })
    public int digits;
    private byte[] str;

    @Setup(Level.Trial)
    public void setUp() {
        str = ("9806543217".repeat((digits + 9) / 10).substring(0, digits)).getBytes(StandardCharsets.ISO_8859_1);
    }

    @Benchmark
    public double m() {
        return JavaDoubleParser.parseDouble(str);
    }
}





