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
package blue.lapis.pore.impl.event.world;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.world.PortalCreateEvent;
import org.spongepowered.api.event.world.WorldEvent;

import java.util.ArrayList;

public class PorePortalCreateEvent extends PortalCreateEvent {

    private final WorldEvent handle;

    public PorePortalCreateEvent(WorldEvent handle) {
        super(null, null, null);
        this.handle = checkNotNull(handle, "handle");
    }

    public WorldEvent getHandle() {
        return handle;
    }

    @Override
    public World getWorld() {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public ArrayList<Block> getBlocks() {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public CreateReason getReason() {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public boolean isCancelled() {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public void setCancelled(boolean cancel) {
        throw new NotImplementedException(); // TODO
    }

}
