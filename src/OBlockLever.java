
public class OBlockLever extends OBlock {

    protected OBlockLever(int i, int j) {
        super(i, j, OMaterial.p);
    }

    public OAxisAlignedBB e(OWorld oworld, int i, int j, int k) {
        return null;
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return false;
    }

    public int c() {
        return 12;
    }

    public boolean b(OWorld oworld, int i, int j, int k, int l) {
        return l == 1 && oworld.e(i, j - 1, k) ? true : (l == 2 && oworld.e(i, j, k + 1) ? true : (l == 3 && oworld.e(i, j, k - 1) ? true : (l == 4 && oworld.e(i + 1, j, k) ? true : l == 5 && oworld.e(i - 1, j, k))));
    }

    public boolean c(OWorld oworld, int i, int j, int k) {
        return oworld.e(i - 1, j, k) ? true : (oworld.e(i + 1, j, k) ? true : (oworld.e(i, j, k - 1) ? true : (oworld.e(i, j, k + 1) ? true : oworld.e(i, j - 1, k))));
    }

    public void e(OWorld oworld, int i, int j, int k, int l) {
        int i1 = oworld.c(i, j, k);
        int j1 = i1 & 8;

        i1 &= 7;
        i1 = -1;
        if (l == 1 && oworld.e(i, j - 1, k)) {
            i1 = 5 + oworld.r.nextInt(2);
        }

        if (l == 2 && oworld.e(i, j, k + 1)) {
            i1 = 4;
        }

        if (l == 3 && oworld.e(i, j, k - 1)) {
            i1 = 3;
        }

        if (l == 4 && oworld.e(i + 1, j, k)) {
            i1 = 2;
        }

        if (l == 5 && oworld.e(i - 1, j, k)) {
            i1 = 1;
        }

        if (i1 == -1) {
            this.b(oworld, i, j, k, oworld.c(i, j, k), 0);
            oworld.e(i, j, k, 0);
        } else {
            oworld.c(i, j, k, i1 + j1);
        }
    }

    public void a(OWorld oworld, int i, int j, int k, int l) {
        if (this.g(oworld, i, j, k)) {
            int i1 = oworld.c(i, j, k) & 7;
            boolean flag = false;

            if (!oworld.e(i - 1, j, k) && i1 == 1) {
                flag = true;
            }

            if (!oworld.e(i + 1, j, k) && i1 == 2) {
                flag = true;
            }

            if (!oworld.e(i, j, k - 1) && i1 == 3) {
                flag = true;
            }

            if (!oworld.e(i, j, k + 1) && i1 == 4) {
                flag = true;
            }

            if (!oworld.e(i, j - 1, k) && i1 == 5) {
                flag = true;
            }

            if (!oworld.e(i, j - 1, k) && i1 == 6) {
                flag = true;
            }

            if (flag) {
                this.b(oworld, i, j, k, oworld.c(i, j, k), 0);
                oworld.e(i, j, k, 0);
            }
        }

    }

    private boolean g(OWorld oworld, int i, int j, int k) {
        if (!this.c(oworld, i, j, k)) {
            this.b(oworld, i, j, k, oworld.c(i, j, k), 0);
            oworld.e(i, j, k, 0);
            return false;
        } else {
            return true;
        }
    }

    public void a(OIBlockAccess oiblockaccess, int i, int j, int k) {
        int l = oiblockaccess.c(i, j, k) & 7;
        float f = 0.1875F;

        if (l == 1) {
            this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        } else if (l == 2) {
            this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        } else if (l == 3) {
            this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        } else if (l == 4) {
            this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        } else {
            f = 0.25F;
            this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }

    }

    public void b(OWorld oworld, int i, int j, int k, OEntityPlayer oentityplayer) {
        this.a(oworld, i, j, k, oentityplayer);
    }

    public boolean a(OWorld oworld, int i, int j, int k, OEntityPlayer oentityplayer) {
        if (oworld.F) {
            return true;
        } else {
            int l = oworld.c(i, j, k);
            int i1 = l & 7;
            int j1 = 8 - (l & 8);
            // CanaryMod: Allow the lever to change the current first 3 bits are for postion 4th bit is for power. (on / off)
            int old = (j1 != 8) ? 1 : 0;
            int current = (j1 == 8) ? 1 : 0;

            current = ((Integer) etc.getLoader().callHook(PluginLoader.Hook.REDSTONE_CHANGE, new Block(oworld.world, bO, i, j, k), old, current)).intValue();
            j1 = (current > 0) ? 8 : 0;

            oworld.c(i, j, k, i1 + j1);
            oworld.b(i, j, k, i, j, k);
            oworld.a((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.click", 0.3F, j1 > 0 ? 0.6F : 0.5F);
            oworld.h(i, j, k, this.bO);
            if (i1 == 1) {
                oworld.h(i - 1, j, k, this.bO);
            } else if (i1 == 2) {
                oworld.h(i + 1, j, k, this.bO);
            } else if (i1 == 3) {
                oworld.h(i, j, k - 1, this.bO);
            } else if (i1 == 4) {
                oworld.h(i, j, k + 1, this.bO);
            } else {
                oworld.h(i, j - 1, k, this.bO);
            }

            return true;
        }
    }

    public void d(OWorld oworld, int i, int j, int k) {
        int l = oworld.c(i, j, k);

        if ((l & 8) > 0) {
            oworld.h(i, j, k, this.bO);
            int i1 = l & 7;

            if (i1 == 1) {
                oworld.h(i - 1, j, k, this.bO);
            } else if (i1 == 2) {
                oworld.h(i + 1, j, k, this.bO);
            } else if (i1 == 3) {
                oworld.h(i, j, k - 1, this.bO);
            } else if (i1 == 4) {
                oworld.h(i, j, k + 1, this.bO);
            } else {
                oworld.h(i, j - 1, k, this.bO);
            }
        }

        super.d(oworld, i, j, k);
    }

    public boolean a(OIBlockAccess oiblockaccess, int i, int j, int k, int l) {
        return (oiblockaccess.c(i, j, k) & 8) > 0;
    }

    public boolean d(OWorld oworld, int i, int j, int k, int l) {
        int i1 = oworld.c(i, j, k);

        if ((i1 & 8) == 0) {
            return false;
        } else {
            int j1 = i1 & 7;

            return j1 == 6 && l == 1 ? true : (j1 == 5 && l == 1 ? true : (j1 == 4 && l == 2 ? true : (j1 == 3 && l == 3 ? true : (j1 == 2 && l == 4 ? true : j1 == 1 && l == 5))));
        }
    }

    public boolean e() {
        return true;
    }
}
