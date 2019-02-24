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
package com.github.korthout.cantis;

import java.util.stream.Stream;
import lombok.NonNull;

/**
 * A list of definitions in a codebase.
 * @since 0.1
 */
public final class CodebaseGlossary implements Glossary {

    /**
     * The types from which to build the glossary.
     */
    private final Stream<Type> types;

    /**
     * Main Constructor.
     * @param types The types from which to build the glossary
     */
    CodebaseGlossary(final @NonNull Stream<Type> types) {
        this.types = types;
    }

    /**
     * Constructor.
     * @param codebase The codebase containing types
     */
    CodebaseGlossary(final Codebase codebase) {
        this(codebase.types());
    }

    @Override
    public Stream<Definition> definitions() {
        return this.types
            .filter(Type::hasGlossaryTermAnnotation)
            .filter(Type::hasJavadoc)
            .map(Type::definition);
    }

}
