/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Nico Korthout
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.korthout.cantis.from_java.commandline;

import com.github.korthout.cantis.from_java.codebase.Directory;
import com.github.korthout.cantis.from_java.glossary.GlossaryPrinter;
import com.github.korthout.cantis.from_java.output.ToFile;
import com.github.korthout.cantis.from_java.output.ToPrintStream;
import com.github.korthout.cantis.from_java.formatting.Format;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

/**
 * Commandline command to generate a glossary from sourcecode.
 * @since 0.2
 */
@Command(
    name = "generate",
    description = "Generate a glossary from your sourcecode"
)
// mutability required by airline
@SuppressWarnings("PMD.ImmutableField")
public final class Generate implements Runnable {

    /**
     * Optional argument: The root directory of the source code.
     * Defaults to {@code ./}.
     */
    @Arguments(
        title = "source",
        description = "The root directory of the source code"
    )
    private String source;

    /**
     * Optional argument: Path to output file.
     */
    @Option(
        title = "target",
        name = {"--target", "-t"},
        description = "Path to output file"
    )
    private String target;

    /**
     * Optional argument: Output format.
     * Defaults to {@code Format.PLAIN}.
     */
    @Option(
        title = "format",
        name = {"--format", "-f"},
        description = "Output format (options: json, plain; default: plain)"
    )
    private Format format;

    /**
     * Main Constructor.
     */
    Generate() {
        this.source = ".";
        this.target = "";
        this.format = Format.PLAIN;
    }

    @Override
    public void run() {
        new GlossaryPrinter(
            new Directory.FromSource(this.source),
            new ToPrintStream(System.out),
            // @checkstyle AvoidInlineConditionals (3 lines)
            this.target.isBlank()
                ? new ToPrintStream(System.out)
                : new ToFile(this.target),
            this.format
        ).print();
    }
}
