package com.pocolifo.pocolifoclient.launch;

import com.pocolifo.obfuscator.annotations.Pass;
import com.pocolifo.obfuscator.annotations.PassOption;

@Pass(value = "RemapNamesPass", options = {
        @PassOption(key = "remapClassNames", value = "false")
})
public class IsInProduction { }
