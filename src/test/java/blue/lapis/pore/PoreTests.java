/*
 * Pore
 * Copyright (c) 2014-2015, Lapis <https://github.com/LapisBlue>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blue.lapis.pore;

import static org.mockito.Mockito.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class PoreTests {

    public static void mockPlugin() {
        if (Pore.instance == null) {
            Pore pore = new Pore();
            Pore.instance = pore;
            pore.game = mock(Game.class);
            pore.logger = LoggerFactory.getLogger("Pore");
            Pore.testLogger = pore.logger;
        }
    }

    public static Logger getLogger() {
        mockPlugin();
        return Pore.getTestLogger();
    }

    private static Field modifiers;

    public static void setConstants(Class<?>... classes) throws Exception {
        if (modifiers == null) {
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            PoreTests.modifiers = modifiers;
        }

        for (Class<?> type : classes) {
            for (Field field : type.getFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    if (Modifier.isFinal(field.getModifiers())) {
                        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    }

                    field.set(null, mock(field.getType()));
                }
            }
        }
    }

}
