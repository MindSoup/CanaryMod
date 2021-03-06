import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class OChunkProviderServer implements OIChunkProvider {

    private Set b = new HashSet();
    private OChunk c;
    private OIChunkProvider d;
    private OIChunkLoader e;
    public boolean a = false;
    private OLongHashMap f = new OLongHashMap();
    private List g = new ArrayList();
    private OWorldServer h;
    // CanaryMod: load status
    boolean loaded = false;
    boolean loadedpreload = false;

    public OChunkProviderServer(OWorldServer oworldserver, OIChunkLoader oichunkloader, OIChunkProvider oichunkprovider) {
        super();
        this.c = new OEmptyChunk(oworldserver, 0, 0);
        this.h = oworldserver;
        this.e = oichunkloader;
        this.d = oichunkprovider;
    }

    public boolean a(int i, int j) {
        return this.f.b(OChunkCoordIntPair.a(i, j));
    }

    public void d(int i, int j) {
        if (this.h.t.c()) {
            OChunkCoordinates ochunkcoordinates = this.h.p();
            int k = i * 16 + 8 - ochunkcoordinates.a;
            int l = j * 16 + 8 - ochunkcoordinates.c;
            short short1 = 128;

            if (k < -short1 || k > short1 || l < -short1 || l > short1) {
                this.b.add(Long.valueOf(OChunkCoordIntPair.a(i, j)));
            }
        } else {
            this.b.add(Long.valueOf(OChunkCoordIntPair.a(i, j)));
        }

    }

    public void c() {
        Iterator iterator = this.g.iterator();

        while (iterator.hasNext()) {
            OChunk ochunk = (OChunk) iterator.next();

            this.d(ochunk.g, ochunk.h);
        }

    }

    public OChunk c(int i, int j) {
        long k = OChunkCoordIntPair.a(i, j);

        this.b.remove(Long.valueOf(k));
        OChunk ochunk = (OChunk) this.f.a(k);

        if (ochunk == null) {
            // CanaryMod: load preload plugins once!
            if (!loadedpreload) {
                etc.getLoader().loadPreloadPlugins();
                loadedpreload = true;
            }
            ochunk = this.e(i, j);
            if (ochunk == null) {
                // Canary onChunkCreate hook
                byte[] blocks = (byte[]) etc.getLoader().callHook(PluginLoader.Hook.CHUNK_CREATE, i, j, h.world);

                if (blocks != null) {
                    ochunk = Chunk.getNewChunk(h, blocks, i, j).chunk;
                    ochunk.k = true; // is populated = true
                    ochunk.b(); // lightning update
                } else if (this.d == null) {
                    ochunk = this.c;
                } else {
                    ochunk = this.d.b(i, j);
                }

                etc.getLoader().callHook(PluginLoader.Hook.CHUNK_CREATED, ochunk.chunk);
            }

            this.f.a(k, ochunk);
            this.g.add(ochunk);
            if (ochunk != null) {
                ochunk.b();
                ochunk.c();
                // Canary onChunkLoaded hook
                etc.getLoader().callHook(PluginLoader.Hook.CHUNK_LOADED, ochunk.chunk);
                if (!ochunk.k && this.a(i + 1, j + 1) && this.a(i, j + 1) && this.a(i + 1, j)) {
                    this.a(this, i, j);
                }
            }// To prevent NullPointerExceptions
            if (this.a(i - 1, j) && !this.b(i - 1, j).k && this.a(i - 1, j + 1) && this.a(i, j + 1) && this.a(i - 1, j)) {
                this.a(this, i - 1, j);
            }

            ochunk.a(this, this, i, j);
        }

        return ochunk;
    }

    public OChunk b(int i, int j) {
        OChunk ochunk = (OChunk) this.f.a(OChunkCoordIntPair.a(i, j));

        return ochunk == null ? (!this.h.y && !this.a ? this.c : this.c(i, j)) : ochunk;
    }

    private OChunk e(int i, int j) {
        if (this.e == null) {
            return null;
        } else {
            try {
                OChunk ochunk = this.e.a(this.h, i, j);

                if (ochunk != null) {
                    ochunk.n = this.h.o();
                }

                return ochunk;
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }

    private void a(OChunk ochunk) {
        if (this.e != null) {
            try {
                this.e.b(this.h, ochunk);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }

    private void b(OChunk ochunk) {
        if (this.e != null) {
            ochunk.n = this.h.o();
            this.e.a(this.h, ochunk);
        }
    }

    public void a(OIChunkProvider oichunkprovider, int i, int j) {
        OChunk ochunk = this.b(i, j);

        if (!ochunk.k) {
            ochunk.k = true;
            if (this.d != null) {
                this.d.a(oichunkprovider, i, j);
                ochunk.e();
            }
        }

    }

    public boolean a(boolean flag, OIProgressUpdate oiprogressupdate) {
        // CanaryMod: load once!
        if (!loaded) {
            etc.getLoader().loadPlugins();
            loaded = true;
        }
		
        int i = 0;

        for (int j = 0; j < this.g.size(); ++j) {
            OChunk ochunk = (OChunk) this.g.get(j);

            if (flag) {
                this.a(ochunk);
            }

            if (ochunk.a(flag)) {
                this.b(ochunk);
                ochunk.l = false;
                ++i;
                if (i == 24 && !flag) {
                    return false;
                }
            }
        }

        if (flag) {
            if (this.e == null) {
                return true;
            }

            this.e.b();
        }

        return true;
    }

    public boolean a() {
        if (!this.h.I) {
            for (int i = 0; i < 100; ++i) {
                if (!this.b.isEmpty()) {
                    Long olong = (Long) this.b.iterator().next();
                    OChunk ochunk = (OChunk) this.f.a(olong.longValue());

                    // Canary onChunkUnload hook
                    etc.getLoader().callHook(PluginLoader.Hook.CHUNK_UNLOAD, ochunk.chunk);
                    ochunk.d();
                    this.b(ochunk);
                    this.a(ochunk);
                    this.b.remove(olong);
                    this.f.d(olong.longValue());
                    this.g.remove(ochunk);
                }
            }

            if (this.e != null) {
                this.e.a();
            }
        }

        return this.d.a();
    }
	
    public OChunk regenerateChunk(int i, int j) {
        Long chunkCoordIntPair = OChunkCoordIntPair.a(i, j);
        
        // Unloading the chunk
        OChunk unloadedChunk = (OChunk) f.a(chunkCoordIntPair.longValue());

        if (unloadedChunk != null) {
            unloadedChunk.e();
            b(unloadedChunk);
            a(unloadedChunk);
            b.remove(chunkCoordIntPair);
            f.d(chunkCoordIntPair.longValue());
            g.remove(unloadedChunk);
        }
        
        // Generating the new chunk
        OChunk newChunk = d.b(i, j);

        f.a(chunkCoordIntPair, newChunk);
        g.add(newChunk);
        if (newChunk != null) {
            newChunk.c();
            newChunk.d();
        }
        newChunk.a(this, this, i, j);
        
        // Save the new chunk, overriding the old one
        a(newChunk);
        b(newChunk);
        newChunk.k = false;
        if (e != null) {
            e.b();
        }
        
        return newChunk;
    }

    public boolean b() {
        return !this.h.I;
    }

    public String d() {
        return "ServerChunkCache: " + this.f.a() + " Drop: " + this.b.size();
    }

    public List a(OEnumCreatureType oenumcreaturetype, int i, int j, int k) {
        return this.d.a(oenumcreaturetype, i, j, k);
    }

    public OChunkPosition a(OWorld oworld, String s, int i, int j, int k) {
        return this.d.a(oworld, s, i, j, k);
    }
}
