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
package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Instrument;
import org.bukkit.block.NoteBlock;
import org.spongepowered.api.block.data.Note;

public class PoreNoteBlock extends PoreBlockState implements NoteBlock {

    public static PoreNoteBlock of(Note handle) {
        return WrapperConverter.of(PoreNoteBlock.class, handle);
    }

    protected PoreNoteBlock(Note handle) {
        super(handle);
    }

    @Override
    public Note getHandle() {
        return (Note) super.getHandle();
    }

    @Override
    public org.bukkit.Note getNote() {
        throw new NotImplementedException();
    }

    @Override
    public byte getRawNote() {
        throw new NotImplementedException();
    }

    @Override
    public void setNote(org.bukkit.Note note) {
        throw new NotImplementedException();
    }

    @Override
    public void setRawNote(byte note) {
        throw new NotImplementedException();
    }

    @Override
    public boolean play() {
        throw new NotImplementedException();
    }

    @Override
    public boolean play(byte instrument, byte note) {
        throw new NotImplementedException();
    }

    @Override
    public boolean play(Instrument instrument, org.bukkit.Note note) {
        throw new NotImplementedException();
    }
}
