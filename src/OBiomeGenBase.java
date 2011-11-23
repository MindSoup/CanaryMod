import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class OBiomeGenBase {

   public static final OBiomeGenBase[] a = new OBiomeGenBase[256];
   public static final OBiomeGenBase b = (new OBiomeGenOcean(0)).b(112).a("Ocean").b(-1.0F, 0.4F);
   public static final OBiomeGenBase c = (new OBiomeGenPlains(1)).b(9286496).a("Plains").a(0.8F, 0.4F);
   public static final OBiomeGenBase d = (new OBiomeGenDesert(2)).b(16421912).a("Desert").g().a(2.0F, 0.0F).b(0.1F, 0.2F);
   public static final OBiomeGenBase e = (new OBiomeGenHills(3)).b(6316128).a("Extreme Hills").b(0.2F, 1.8F).a(0.2F, 0.3F);
   public static final OBiomeGenBase f = (new OBiomeGenForest(4)).b(353825).a("Forest").a(5159473).a(0.7F, 0.8F);
   public static final OBiomeGenBase g = (new OBiomeGenTaiga(5)).b(747097).a("Taiga").a(5159473).a(0.3F, 0.8F).b(0.1F, 0.4F);
   public static final OBiomeGenBase h = (new OBiomeGenSwamp(6)).b(522674).a("Swampland").a(9154376).b(-0.2F, 0.1F).a(0.8F, 0.9F);
   public static final OBiomeGenBase i = (new OBiomeGenRiver(7)).b(255).a("River").b(-0.5F, 0.0F);
   public static final OBiomeGenBase j = (new OBiomeGenHell(8)).b(16711680).a("Hell").g().a(2.0F, 0.0F);
   public static final OBiomeGenBase k = (new OBiomeGenSky(9)).b(8421631).a("Sky").g();
   public static final OBiomeGenBase l = (new OBiomeGenOcean(10)).b(9474208).a("FrozenOcean").b(-1.0F, 0.5F).a(0.0F, 0.5F);
   public static final OBiomeGenBase m = (new OBiomeGenRiver(11)).b(10526975).a("FrozenRiver").b(-0.5F, 0.0F).a(0.0F, 0.5F);
   public static final OBiomeGenBase n = (new OBiomeGenSnow(12)).b(16777215).a("Ice Plains").a(0.0F, 0.5F);
   public static final OBiomeGenBase o = (new OBiomeGenSnow(13)).b(10526880).a("Ice Mountains").b(0.2F, 1.8F).a(0.0F, 0.5F);
   public static final OBiomeGenBase p = (new OBiomeGenMushroomIsland(14)).b(16711935).a("MushroomIsland").a(0.9F, 1.0F).b(0.2F, 1.0F);
   public static final OBiomeGenBase q = (new OBiomeGenMushroomIsland(15)).b(10486015).a("MushroomIslandShore").a(0.9F, 1.0F).b(-1.0F, 0.1F);
   public String r;
   public int s;
   public byte t;
   public byte u;
   public int v;
   public float w;
   public float x;
   public float y;
   public float z;
   public int A;
   public OBiomeDecorator B;
   protected List C;
   protected List D;
   protected List E;
   private boolean K;
   private boolean L;
   public final int F;
   protected OWorldGenTrees G;
   protected OWorldGenBigTree H;
   protected OWorldGenForest I;
   protected OWorldGenSwamp J;


   protected OBiomeGenBase(int var1) {
      super();
      this.t = (byte)OBlock.u.bM;
      this.u = (byte)OBlock.v.bM;
      this.v = 5169201;
      this.w = 0.1F;
      this.x = 0.3F;
      this.y = 0.5F;
      this.z = 0.5F;
      this.A = 16777215;
      this.C = new ArrayList();
      this.D = new ArrayList();
      this.E = new ArrayList();
      this.L = true;
      this.G = new OWorldGenTrees(false);
      this.H = new OWorldGenBigTree(false);
      this.I = new OWorldGenForest(false);
      this.J = new OWorldGenSwamp();
      this.F = var1;
      a[var1] = this;
      this.B = this.a();
      this.D.add(new OSpawnListEntry(OEntitySheep.class, 12, 4, 4));
      this.D.add(new OSpawnListEntry(OEntityPig.class, 10, 4, 4));
      this.D.add(new OSpawnListEntry(OEntityChicken.class, 10, 4, 4));
      this.D.add(new OSpawnListEntry(OEntityCow.class, 8, 4, 4));
      this.C.add(new OSpawnListEntry(OEntitySpider.class, 10, 4, 4));
      this.C.add(new OSpawnListEntry(OEntityZombie.class, 10, 4, 4));
      this.C.add(new OSpawnListEntry(OEntitySkeleton.class, 10, 4, 4));
      this.C.add(new OSpawnListEntry(OEntityCreeper.class, 10, 4, 4));
      this.C.add(new OSpawnListEntry(OEntitySlime.class, 10, 4, 4));
      this.C.add(new OSpawnListEntry(OEntityEnderman.class, 1, 1, 4));
      this.E.add(new OSpawnListEntry(OEntitySquid.class, 10, 4, 4));
   }

   protected OBiomeDecorator a() {
      return new OBiomeDecorator(this);
   }

   private OBiomeGenBase a(float var1, float var2) {
      if(var1 > 0.1F && var1 < 0.2F) {
         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
      } else {
         this.y = var1;
         this.z = var2;
         return this;
      }
   }

   private OBiomeGenBase b(float var1, float var2) {
      this.w = var1;
      this.x = var2;
      return this;
   }

   private OBiomeGenBase g() {
      this.L = false;
      return this;
   }

   public OWorldGenerator a(Random var1) {
      return (OWorldGenerator)(var1.nextInt(10) == 0?this.H:this.G);
   }

   protected OBiomeGenBase a(String var1) {
      this.r = var1;
      return this;
   }

   protected OBiomeGenBase a(int var1) {
      this.v = var1;
      return this;
   }

   protected OBiomeGenBase b(int var1) {
      this.s = var1;
      return this;
   }
   
   // CanaryMod start - responsible for adding spawned monsters to the monster list.
   public List a(OEnumCreatureType var1) {
       etc config = etc.getInstance();
       if (var1 == OEnumCreatureType.a)
           return config.getMonstersClass(this);
       if (var1 == OEnumCreatureType.b)
           return config.getAnimalsClass(this);
       if (var1 == OEnumCreatureType.c)
           return config.getWaterAnimalsClass(this);
       return null;
   }

   // CanaryMod end

   public boolean b() {
      return this.K;
   }

   public boolean c() {
      return this.K?false:this.L;
   }

   public float d() {
      return 0.1F;
   }

   public final int e() {
      return (int)(this.z * 65536.0F);
   }

   public final int f() {
      return (int)(this.y * 65536.0F);
   }

   public void a(OWorld var1, Random var2, int var3, int var4) {
      this.B.a(var1, var2, var3, var4);
   }

}
