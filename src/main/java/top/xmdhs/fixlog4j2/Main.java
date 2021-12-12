package top.xmdhs.fixlog4j2;

import java.lang.instrument.Instrumentation;

public class Main {
    public static void premain(String agentArgs, Instrumentation inst){
        inst.addTransformer(new Transformer());
    }
}
