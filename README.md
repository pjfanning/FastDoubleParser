# FastDoubleParser

This is a Java port of Daniel Lemire's fast_float.

https://github.com/fastfloat/fast_float

Usage:

    import ch.randelshofer.fastdoubleparser.FastDoubleParser;
    import ch.randelshofer.fastdoubleparser.FastFloatParser;

    double d = FastDoubleParser.parseDouble("1.2345");
    float f = FastFloatParser.parseFloat("1.2345");

Method `parseDouble()` takes a `CharacterSequence`. a `char`-array or a `byte`-array as argument. This way. you can
parse from a `StringBuffer` or an array without having to convert your input to a `String`. Parsing from an array is
faster. because the parser can process multiple characters at once using SIMD instructions.

When you clone the code repository from github. you can choose from the following branches:

- `main` The code in this branch requires Java 17.
- `java8` The code in this branch requires Java 8.
- `dev` This code may or may not work. This code uses the experimental Vector API, and the Foreign Memory Access API,
  that are included in Java 19.

How to run the performance tests on a Mac:

1. Install Java JDK 8 or higher. for example [OpenJDK Java 18](https://jdk.java.net/18/)
2. Install the XCode command line tools from Apple.
3. Open the Terminal and execute the following commands:

Command sequence for Java 18 or higher:

     git clone https://github.com/wrandelshofer/FastDoubleParser.git
     cd FastDoubleParser 
     javac --enable-preview -source 18 -d out -encoding utf8 --module-source-path src/main/java --module ch.randelshofer.fastdoubleparser    
     javac --enable-preview -source 18 -d out -encoding utf8 -p out --module-source-path FastDoubleParserDemo/src/main/java --module ch.randelshofer.fastdoubleparserdemo
     java --enable-preview -p out -m ch.randelshofer.fastdoubleparserdemo/ch.randelshofer.fastdoubleparserdemo.Main  
     java --enable-preview -p out -m ch.randelshofer.fastdoubleparserdemo/ch.randelshofer.fastdoubleparserdemo.Main data/canada.txt   

Command sequence for Java 8 or higher:

     git clone https://github.com/wrandelshofer/FastDoubleParser.git
     cd FastDoubleParser 
     git checkout java8
     mkdir out
     javac -d out -encoding utf8 -sourcepath src/main/java/ch.randelshofer.fastdoubleparser src/main/java/ch.randelshofer.fastdoubleparser/**/*.java    
     javac -d out -encoding utf8 -cp out -sourcepath FastDoubleParserDemo/src/main/java/ch.randelshofer.fastdoubleparserdemo FastDoubleParserDemo/src/main/java/ch.randelshofer.fastdoubleparserdemo/**/*.java
     java -cp out ch.randelshofer.fastdoubleparserdemo.Main  
     java -cp out ch.randelshofer.fastdoubleparserdemo.Main data/canada.txt   

On my Mac mini (2018) I get the results shown below. The speedup factor with respect to `Double.parseDouble` ranges from
0.5 to 6 depending on the shape of the input data. You can expect a speedup factor of 4 for common input data shapes.

    Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz
    x86_64, Mac OS X, 12.5.1, 12
    OpenJDK 64-Bit Server VM, Oracle Corporation, 25.281-b09
    -XX:+UnlockExperimentalVMOptions
    Parsing random doubles in the range [0,1).
    [...]
    Measuring: Trying to reach a confidence level of 99.8 % which only deviates by 1 % from the average measured duration.
    [...]
    Measurement results:
    java.lang.Double            :    75.18 MB/s (+/- 4.0 %)     4.32 Mfloat/s     231.73 ns/f
    java.lang.Float             :    78.68 MB/s (+/- 3.1 %)     4.52 Mfloat/s     221.41 ns/f
    java.math.BigDecimal        :   162.24 MB/s (+/- 4.0 %)     9.31 Mfloat/s     107.37 ns/f
    JavaDoubleParser String     :   325.57 MB/s (+/- 7.0 %)    18.69 Mfloat/s      53.51 ns/f
    JavaDoubleParser char[]     :   494.40 MB/s (+/- 7.6 %)    28.38 Mfloat/s      35.24 ns/f
    JavaDoubleParser byte[]     :   508.45 MB/s (+/- 7.2 %)    29.19 Mfloat/s      34.26 ns/f
    JsonDoubleParser String     :   355.22 MB/s (+/- 6.7 %)    20.39 Mfloat/s      49.04 ns/f
    JsonDoubleParser char[]     :   520.54 MB/s (+/- 8.0 %)    29.88 Mfloat/s      33.47 ns/f
    JsonDoubleParser byte[]     :   499.04 MB/s (+/- 6.5 %)    28.65 Mfloat/s      34.91 ns/f
    JavaFloatParser  String     :   328.77 MB/s (+/- 5.9 %)    18.87 Mfloat/s      52.99 ns/f
    JavaFloatParser  char[]     :   485.02 MB/s (+/- 6.8 %)    27.84 Mfloat/s      35.92 ns/f
    JavaFloatParser  byte[]     :   535.15 MB/s (+/- 6.5 %)    30.72 Mfloat/s      32.55 ns/f
    JavaBigDecimalParser String :   376.81 MB/s (+/- 8.6 %)    21.63 Mfloat/s      46.23 ns/f
    JavaBigDecimalParser char[] :   563.99 MB/s (+/- 8.3 %)    32.37 Mfloat/s      30.89 ns/f
    JavaBigDecimalParser byte[] :   609.58 MB/s (+/- 9.2 %)    34.99 Mfloat/s      28.58 ns/f
    
    Speedup JavaDoubleParser String     vs java.lang.Double    : 4.33
    Speedup JavaDoubleParser char[]     vs java.lang.Double    : 6.58
    Speedup JavaDoubleParser byte[]     vs java.lang.Double    : 6.76
    Speedup JsonDoubleParser String     vs java.lang.Double    : 4.73
    Speedup JsonDoubleParser char[]     vs java.lang.Double    : 6.92
    Speedup JsonDoubleParser byte[]     vs java.lang.Double    : 6.64
    Speedup JavaFloatParser  String     vs java.lang.Float     : 4.18
    Speedup JavaFloatParser  char[]     vs java.lang.Float     : 6.16
    Speedup JavaFloatParser  byte[]     vs java.lang.Float     : 6.80
    Speedup JavaBigDecimalParser String vs java.math.BigDecimal: 2.32
    Speedup JavaBigDecimalParser char[] vs java.math.BigDecimal: 3.48
    Speedup JavaBigDecimalParser byte[] vs java.math.BigDecimal: 3.76


'

    parsing numbers in file /Users/Shared/Developer/Java/FastDoubleParser/github/FastDoubleParser/data/canada.txt
    read 111126 lines
    [...]
    Measurement results:
    java.lang.Double            :    82.29 MB/s (+/- 2.6 %)     4.73 Mfloat/s     211.47 ns/f
    java.lang.Float             :    91.00 MB/s (+/- 3.0 %)     5.23 Mfloat/s     191.22 ns/f
    java.math.BigDecimal        :   235.28 MB/s (+/- 4.7 %)    13.52 Mfloat/s      73.96 ns/f
    JavaDoubleParser String     :   317.54 MB/s (+/- 6.8 %)    18.25 Mfloat/s      54.80 ns/f
    JavaDoubleParser char[]     :   475.91 MB/s (+/- 7.1 %)    27.35 Mfloat/s      36.56 ns/f
    JavaDoubleParser byte[]     :   499.25 MB/s (+/- 6.0 %)    28.69 Mfloat/s      34.86 ns/f
    JsonDoubleParser String     :   342.31 MB/s (+/- 6.8 %)    19.67 Mfloat/s      50.83 ns/f
    JsonDoubleParser char[]     :   417.02 MB/s (+/- 6.6 %)    23.97 Mfloat/s      41.73 ns/f
    JsonDoubleParser byte[]     :   488.41 MB/s (+/- 5.9 %)    28.07 Mfloat/s      35.63 ns/f
    JavaFloatParser  String     :   338.17 MB/s (+/- 5.5 %)    19.43 Mfloat/s      51.46 ns/f
    JavaFloatParser  char[]     :   522.22 MB/s (+/- 7.3 %)    30.01 Mfloat/s      33.32 ns/f
    JavaFloatParser  byte[]     :   549.39 MB/s (+/- 6.7 %)    31.57 Mfloat/s      31.67 ns/f
    JavaBigDecimalParser String :   401.17 MB/s (+/- 6.6 %)    23.05 Mfloat/s      43.38 ns/f
    JavaBigDecimalParser char[] :   553.33 MB/s (+/- 7.5 %)    31.80 Mfloat/s      31.45 ns/f
    JavaBigDecimalParser byte[] :   583.50 MB/s (+/- 7.5 %)    33.53 Mfloat/s      29.82 ns/f
    
    Speedup JavaDoubleParser String     vs java.lang.Double    : 3.86
    Speedup JavaDoubleParser char[]     vs java.lang.Double    : 5.78
    Speedup JavaDoubleParser byte[]     vs java.lang.Double    : 6.07
    Speedup JsonDoubleParser String     vs java.lang.Double    : 4.16
    Speedup JsonDoubleParser char[]     vs java.lang.Double    : 5.07
    Speedup JsonDoubleParser byte[]     vs java.lang.Double    : 5.94
    Speedup JavaFloatParser  String     vs java.lang.Float     : 3.72
    Speedup JavaFloatParser  char[]     vs java.lang.Float     : 5.74
    Speedup JavaFloatParser  byte[]     vs java.lang.Float     : 6.04
    Speedup JavaBigDecimalParser String vs java.math.BigDecimal: 1.71
    Speedup JavaBigDecimalParser char[] vs java.math.BigDecimal: 2.35
    Speedup JavaBigDecimalParser byte[] vs java.math.BigDecimal: 2.48

FastDoubleParser also speeds up parsing of hexadecimal float literals:

    parsing numbers in file /Users/Shared/Developer/Java/FastDoubleParser/github/FastDoubleParser/data/canada_hex.txt
    read 111126 lines
    [...]
    Measurement results:
    java.lang.Double            :    38.45 MB/s (+/- 5.1 %)     2.11 Mfloat/s     474.29 ns/f
    java.lang.Float             :    39.15 MB/s (+/- 4.2 %)     2.15 Mfloat/s     465.82 ns/f
    JavaDoubleParser String     :   407.92 MB/s (+/- 8.1 %)    22.37 Mfloat/s      44.71 ns/f
    JavaDoubleParser char[]     :   605.77 MB/s (+/- 9.0 %)    33.21 Mfloat/s      30.11 ns/f
    JavaDoubleParser byte[]     :   640.55 MB/s (+/- 7.7 %)    35.12 Mfloat/s      28.47 ns/f
    JavaFloatParser  String     :   389.53 MB/s (+/- 7.0 %)    21.36 Mfloat/s      46.82 ns/f
    JavaFloatParser  char[]     :   606.53 MB/s (+/- 7.5 %)    33.26 Mfloat/s      30.07 ns/f
    JavaFloatParser  byte[]     :   636.46 MB/s (+/- 7.1 %)    34.90 Mfloat/s      28.66 ns/f
    
    Speedup JavaDoubleParser String     vs java.lang.Double    : 10.61
    Speedup JavaDoubleParser char[]     vs java.lang.Double    : 15.75
    Speedup JavaDoubleParser byte[]     vs java.lang.Double    : 16.66
    Speedup JavaFloatParser  String     vs java.lang.Float     : 9.95
    Speedup JavaFloatParser  char[]     vs java.lang.Float     : 15.49
    Speedup JavaFloatParser  byte[]     vs java.lang.Float     : 16.26

## Comparison of JDK versions

The Y-axis shows Mfloat/s.

![ComparisonOfJvmVersions.png](ComparisonOfJvmVersions.png)

| Method              | 1.8.0_281  | 11.0.8 | 18.0.1.1 | 19-ea | 20-ea | 17.0.3graalvm |
|---------------------|------------|--------|----------|-------|-------|---------------|
| Double              | 4.86       | 5.34   | 5.09     | 5.28  | 4.81  | 6.96          |
| FastDouble String   | 20.30      | 27.83  | 31.18    | 32.59 | 26.15 | 32.74         |
| FastDouble char[]   | 30.60      | 30.68  | 33.58    | 37.20 | 29.95 | 34.32         |
| FastDouble byte[]   | 31.29      | 35.61  | 38.24    | 39.67 | 34.23 | 40.21         |

## Comparison with C version

For comparison. here are the test results
of [simple_fastfloat_benchmark](https://github.com/lemire/simple_fastfloat_benchmark)  
on the same computer:

    version: Thu Mar 31 10:18:12 2022 -0400 f2082bf747eabc0873f2fdceb05f9451931b96dc

    Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz SIMD-256

    $ ./build/benchmarks/benchmark
    # parsing random numbers
    available models (-m): uniform one_over_rand32 simple_uniform32 simple_int32 int_e_int simple_int64 bigint_int_dot_int big_ints 
    model: generate random numbers uniformly in the interval [0.0.1.0]
    volume: 100000 floats
    volume = 2.09808 MB 
    netlib                                  :   317.31 MB/s (+/- 6.0 %)    15.12 Mfloat/s      66.12 ns/f 
    doubleconversion                        :   263.89 MB/s (+/- 4.2 %)    12.58 Mfloat/s      79.51 ns/f 
    strtod                                  :    86.13 MB/s (+/- 3.7 %)     4.10 Mfloat/s     243.61 ns/f 
    abseil                                  :   467.27 MB/s (+/- 9.0 %)    22.27 Mfloat/s      44.90 ns/f 
    fastfloat                               :   880.79 MB/s (+/- 6.6 %)    41.98 Mfloat/s      23.82 ns/f 

    OpenJDK 19+36-2238
    java.lang.Double                        :    88.51 MB/s (+/- 5.5 %)     5.08 Mfloat/s     196.86 ns/f
    JavaDoubleParser String                 :   508.13 MB/s (+/-19.0 %)    29.16 Mfloat/s      34.29 ns/f
    JavaDoubleParser char[]                 :   582.40 MB/s (+/-15.0 %)    33.43 Mfloat/s      29.92 ns/f
    JavaDoubleParser byte[]                 :   657.50 MB/s (+/-13.4 %)    37.74 Mfloat/s      26.50 ns/f

'

    $ ./build/benchmarks/benchmark -f data/canada.txt
    # read 111126 lines 
    volume = 1.93374 MB 
    netlib                                  :   337.79 MB/s (+/- 5.8 %)    19.41 Mfloat/s      51.52 ns/f 
    doubleconversion                        :   254.22 MB/s (+/- 6.0 %)    14.61 Mfloat/s      68.45 ns/f 
    strtod                                  :    73.33 MB/s (+/- 7.1 %)     4.21 Mfloat/s     237.31 ns/f 
    abseil                                  :   411.11 MB/s (+/- 7.3 %)    23.63 Mfloat/s      42.33 ns/f 
    fastfloat                               :   741.32 MB/s (+/- 5.3 %)    42.60 Mfloat/s      23.47 ns/f 

    OpenJDK 19+36-2238
    java.lang.Double                        :    87.50 MB/s (+/- 4.0 %)     5.03 Mfloat/s     198.88 ns/f
    JavaDoubleParser String                 :   446.05 MB/s (+/- 9.5 %)    25.63 Mfloat/s      39.01 ns/f
    JavaDoubleParser char[]                 :   537.01 MB/s (+/- 9.1 %)    30.86 Mfloat/s      32.40 ns/f
    JavaDoubleParser byte[]                 :   582.76 MB/s (+/- 2.3 %)    33.49 Mfloat/s      29.86 ns/f
