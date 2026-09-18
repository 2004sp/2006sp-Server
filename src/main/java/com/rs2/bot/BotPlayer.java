package com.rs2.bot;

import com.rs2.ServerSettings;
import com.rs2.bot.BotCombatLoadoutTask;
import com.rs2.bot.BotTaskPlanningTask;
import com.rs2.bot.BotTaskSelectionTask;
import com.rs2.bot.BotTradeAdvertStartTask;
import com.rs2.bot.ClanWarsBotHideTask;
import com.rs2.bot.ClanWarsBotManager;
import com.rs2.bot.DropPartyBotHideTask;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.model.task.TickTask;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.TextUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class BotPlayer
extends Player {
    private static int createdBotCount = 0;
    public static ArrayList dropPartyBots = new ArrayList();
    public static ArrayList defaultProgressiveBotNames = new ArrayList<String>(Arrays.asList("jconfidence", "not a bot", "large weenis", "god", "cpt bunglord", "lord timppa", "zezima", "the old nite", "binaryminer1", "oyusveyus", "grimfeather"));
    private static ArrayList shuffledBotNamePool = new ArrayList();
    public static ArrayList removedBotNames = new ArrayList();
    public static ArrayList forceResetBotNames = new ArrayList();
    private static ArrayList progressiveBotNameQueue = new ArrayList();
    public static ArrayList botNamePool = new ArrayList<String>(Arrays.asList("sillyninja20", "darkonecut0", "mars noob", "volvovr0", "gagton23", "inkheart", "sock flavour", "sophie72", "reaper 4me", "bessiedog", "brknsprk22", "whyuhackme", "rayvano55", "rayvano98", "zaborgttm", "warrior", "madsbang123", "christensen", "theboz87", "iqoigu2d", "f4ll3n0n3", "xbox360123", "stijnkessel", "kessel1996", "genderoni", "lolimawesome", "amac109", "downhill", "citricabyss", "ceesis", "terry1000666", "bink2468", "roman1248", "abcdefghijk", "superchunklz", "lizzie9", "fast reflexx", "asherasher", "rebagon", "44b19n1v", "staylow", "nismo360", "tornoc109", "thunder20", "crosby 8127", "silhouette", "moltenore3", "lavabomb", "feedu arrows", "soccer28", "mindkampfers", "notsogood", "ashbacher7", "tppcrpg7", "michaelsyer", "cue2hug", "drewno58", "13alls4ck", "j p 13", "111robinson", "heyitstrea", "mccain", "doc control", "fartmaster", "neonarg", "b9501175", "snorlax4", "jasonchou", "xgameboy95", "craig9596", "sheltzor", "karateboy11", "makkiestone", "billymon", "blackspidder", "sn1perh34ded", "jerzeythugz2", "trueset", "mr sgytkonyn", "twoitem", "1archerhbk", "assinass", "tharifica1", "apgart00", "fildherbn", "freeze112", "kno way", "loloklol", "supermacho1", "09101991", "raptor8721", "justindb", "jolo5001", "catfish", "spiker991", "qpwoeiruty", "seasonsdawn", "seasonsend", "jartt2", "jartt1", "uber3pic", "doodoo12345", "user zim", "zacharyh1", "cool kevin50", "4194ka", "freitty", "gamecube", "silver knig0", "yourmom1", "beanroid", "smoke47149", "flitch289", "nooder000", "amazing cake", "iridium", "aliffridzuan", "ridzuan2000", "bartez4", "k4eo9ssi7", "d man 5567", "q1w2e1r2", "be kingless", "hardflip", "yami671", "fbr114", "rowelselidio", "gagoako", "haggishanger", "football1", "z 4 t 1 o n", "sxngaming123", "simbojimbo", "digeryd00", "noob 10010", "dragonoid798", "qwerty798", "logieboi", "wichking", "charlto1", "hiei179", "shadowx123", "seamus10990", "danward", "crusher16619", "doremember", "master15125", "ninjas11", "zarkotics", "saphira123", "skyler is on", "lolbobjo", "daniboom", "12345dani", "do mhamai", "eoin3646", "rikuislove23", "denver23", "patacorow", "globalfun", "killer ox08", "scott08", "gold runmoon", "123456q", "nosweet15", "applecore17", "mjc1997", "sayin68", "joshua123", "somnus53", "antant123", "odysseu5", "gccdu58txs", "white1 ghost", "0622842800", "warriorasas", "bordtennis", "call911fro0b", "232323hi", "i 4 sp3c k0", "anning12", "nifeman20", "fujifilm", "pika power08", "skiller2010", "quharas", "gogos123", "keatson", "dodgers5", "mjordan145", "02092006", "koth4356", "1989919899", "viperwind72", "3iderct1", "faeldora81", "naruto816", "colt thunder", "tc1215", "bayat4life", "ali1234567", "daniel323a", "kxb69qjx", "1 rang3 pwns", "hwdpkonrad", "o1xaddicted", "abc123", "adam402", "498750", "gimberly8", "mrcoolio", "baister88", "meatball", "goodguard5", "29a11b96", "josh12508", "j39256", "bozzzerzzz", "nmwhuzrc", "volcomskate", "volcom", "jonny ere 7", "ince78989", "vlad867", "vladstorm12", "justinhigg", "suilui62", "beastblade12", "hobbsey", "sticklysnipe", "bayview1", "snake 1464", "metalgear", "ghtyrupekj", "jackryan", "hewitje", "halloroan", "pupucachu5", "spencer4123", "8bloodhollow", "ilovejesus", "hunting slea", "h3llfire5", "dds o w n s", "9933230", "mankikned22", "kikulita", "skaleris3", "skaleriukas", "bluejay303", "codrocks", "bloodhalo11", "11272000", "maxsheld", "91330451", "jd4gray", "johnny12", "krisi129", "langbestur", "zadashi", "gearsfan", "jollyspace", "451901", "quekchose111", "5bp9mg", "frog1421", "sexrobot1421", "tomatos1", "drk34695", "chimpanze243", "undead0wner", "talald2", "86251234", "radicalluke", "kelly55", "lakertein", "metheman", "krissu127", "hyundai1", "jmannon00b1", "cheese1", "swirkstas", "rainislopas1", "gnarwaster1", "qrhvoq549", "loveburito", "carter22", "tiglasutru", "t1o2y3o4t5", "pijuitshorty", "marakasaya", "mr funky 121", "joker45", "g man", "william", "vexraill", "austin123", "pbcharger 85", "radilla1", "thepureryan", "121489ine", "lukasky", "lukecool", "dinbolir", "remember1", "swe nintendo", "lew492car", "cody52368", "fr0nt1er", "xxandoxx2552", "liverpool1", "i aduka i", "lolowned", "downlar", "jjasjjas", "cookies ohh", "noseboy", "nickspie", "semmy12", "u rock123412", "themind", "keatermy", "thomas98", "exemped", "ikbenyufei", "sum1udontno9", "kr0iznt0", "dark rune195", "savagelump", "kilosteak4", "lakwit17", "93444900", "pl0xables", "lemonade1", "leeboy262", "niniwee", "kazokas7", "lopas123", "demonza1", "will00115", "lavaminer4", "meandme138", "idrinkshakes", "rfyhyj6", "johnymuffin", "casey88", "rempicool", "admiraal1", "celayer", "bhboca2009", "seflarious", "teslatron", "artedhghtun", "pur3k0911", "footlong", "cooldude x5", "toprak123", "cartoon 101", "kfauynoob", "okas5", "sittur", "el sudado", "sudadera", "joeymaster93", "cookiej1", "thedrewboy12", "123123ha", "savior 4 u", "slutbomb", "jiwonbae1", "flameblaze", "xjordankr96x", "goodboy99", "solomon1103", "09102000", "omfgitsemo", "tr4p34", "messidonasef", "yoyododo", "selesan", "kovalev27", "n0 def own u", "hv650cou", "swarnes", "sntioi004", "snakeboy756", "0014702211", "seaviper101", "willow8520", "bradd1808", "smokey1808", "quintanamore", "momcatakers", "ahmarman", "doggydoodoo", "war4more", "stephens", "m3ntal sh0ck", "nosam123", "tinybird02", "m1l1tary", "kill guys", "janieboy2", "kakhoofd123", "koedijk1", "lolliepop", "uhh dead k", "lehrman", "36dragon20", "izzi29040", "spuddman17", "nearhood", "hotviper6", "applesnakes6", "tyber3796", "nintendo1", "putther", "g9768241", "ynot money", "superdude10", "pl0x die", "c1tyw1ns", "rob1tooby1", "awesomeman2", "rsrownage", "welcome01", "smail154", "economie", "actualglory", "hello123", "x gril8", "timmed1", "north1085", "jjn2008", "maximus ivo", "1210729", "robzicles", "sk8ordie", "semanttinen", "koipallo", "zink22100", "toshiba1", "frylok lol", "baseballftw", "flux wolf", "glowx111", "haddon70", "jamaican13", "clivet goth", "lol1lol1", "celsius91", "jonkka123", "kacj321", "9hjgyt83", "puzzlled456", "4elderwand", "moonbear98", "regitllij", "worsinksteeg", "logisung35", "kingescape22", "7dejulio", "sanka181", "campbell", "chikn kikkr", "dinkdink", "zachdamaniak", "godzilla", "pk mpskiller", "309251270", "pukkajoep", "sk1ttl35", "edparks10", "firemonkey4", "sasarrus", "superman1", "900 monster", "tealcrowd8", "sonicboom367", "wreckem", "burnt c", "shadow123", "lv egils", "qwerty12345", "thorodd", "zgergo", "hieveryone21", "neo34rd", "yl2bfbml", "tobi5q", "fredzio0", "qwerty2k3 n2", "allister3", "james13975", "movingon", "im adored", "99str", "magicpinga12", "freminik", "chirinako", "joshj304", "m0t0r5h0w", "valor226", "empire2", "elliott9900", "meggie26", "kiteofdark", "ralph321", "kyrzani", "wsl63069", "0nly 4t he4d", "marcao18", "kernivore", "born2bewild", "1 jeterfan", "goatmilk", "killer gomez", "0007779", "sto joni", "miljoona1", "kittehgod", "familyguy5", "airman521", "qwerty521", "draskiller", "123579jf", "good guy2222", "jsl1983", "macauqe", "kabbible", "the magius", "steven86", "a007lex1", "shadow581000", "fearless264", "61694jjl", "trevor600000", "trevorlee", "leimz", "konnad123", "mblais5", "nida17", "hheadstrong", "tremayne", "goyzman", "jennifer123", "xleet ninjax", "zachyb11", "adamdude333", "adam1997", "saat pataa", "cra2f52z", "crazyidiot70", "jellybean", "plasma guy4", "maxpower", "taohealin", "insane1992", "uhh omg obby", "llop6123", "edrash1", "alenho", "kent688", "fghjkl46", "123erer0", "rustydog23", "monstrs23", "roblox123", "soldbuffalo", "benxharris", "coen42", "mh2agewi", "jo0 my b33ch", "volcanic1", "stany35", "220198th", "motas23", "keese123", "swordblood3", "moomoo3", "davyandicall", "shakira", "paivmonster", "seven77", "sharpair", "a51thetruth", "lulzsux", "gabo2oo4m", "demon ftwz", "chicken1252", "goodguard4", "phonix260", "dragon123", "bencebence1", "19960227", "trainsgomoo4", "killhill", "lightness178", "xdpowerxd", "magican owns", "0204492600", "sly 155", "dragonballz", "ssj4gogeta95", "eggnogg871", "kerfoogle", "football09", "mattfrank7", "1azsxdcfvgb", "invaliid3", "maolenmunn", "arkanoid0", "ftred432", "anonoumous1", "00100101", "i have wingz", "thibeault", "wolf of warr", "hurricane", "rauan78", "mental223", "kay 0wned", "9971282", "marththemage", "ssbmrules", "hcberryblitz", "uzumaki", "beast rage9", "carbine666", "deadlydude46", "aardf1993", "sharper arro", "imawesome1", "unholy1zero", "1w3e4r5", "i pkernoob i", "kirkyisko", "stift8", "jaapstam200", "takeda iesyu", "h0lmg4rd", "im to low", "england123", "viniciusrub", "1224abb08e", "harris 0066", "james0066", "daretorparn", "volvo245", "didzydang", "hduerw", "idskillet", "ilovehalo1", "cows moo34", "bigbang6", "el brento9", "nightwing", "fireshot co2", "bestest", "kain581", "iecsf3hm", "heffman 13", "jl19605", "peep310", "ilovesanta", "kurithia", "readytodie", "ozzy 496", "shebagirl", "evilpk999", "hidalgo", "joker67tny", "demilover", "putles5", "lacina100", "redstone177", "bb123bb", "1hit1kill567", "1456327", "whiplashfan", "suzukits50", "vyndirya", "sjzipje96", "soapthrail", "killzs11", "i hang kids", "circaman1", "jason9918", "jasonw11", "austinz611", "azorn611", "zumerock", "kirara45", "abyssalite", "un1versal", "hellfire33k", "j067946409", "iron vs maul", "notrated7", "deadly ghoul", "chad1990", "samie497", "walkersv", "sir st77", "spijderkat", "vissen24", "blikbiermine", "medion123", "trixdig", "nixon995", "dragonwar729", "dragonwar123", "sc0rch1n 2h", "jagvet66", "leynell", "tresz134", "blocky ownz", "ryan006", "king batty s", "very bigfoot", "freded", "slyestcat", "zared45", "loko3610", "lucozade12", "edwarrior317", "quietatrange", "alec123", "0 0 0wned 00", "doumdoum11", "jaxnarb", "darkness1", "achilles0009", "nathanok", "r4ngepr0fi", "leidinger", "torbjorn6", "google25me25", "42 to doom", "leeong05", "jammer8009", "babybear", "standi96", "schakal1", "sucuraboii27", "ducks66", "15 years now", "bleachfan", "xuan yu31", "dcvhhvcd123", "jazabar", "772892ex", "arch 4 dutch", "zwart123", "dizzy loot", "14racing", "grimmm125", "budgie88", "chrisj144", "chrisj92298", "sir arfa", "arfaiscoolio", "pvp rangers2", "skaiciuokle", "aka t rex", "kingdomkey0", "uncletom96", "cheese96", "matrix men33", "matukka", "haleyboy1", "charlie21", "roooooosa", "kakajunn4444", "sint gabriel", "iamsogoodnow", "nightwolf06", "thelandshark", "sergioshm", "wsaq3214", "sxe ickis", "32210t86p", "sterling1717", "calvin1", "dwarfman666", "mynameis", "romeo beller", "jess987", "bandion", "elhipfop", "adam josiah", "vultures", "majin125", "q78j2rps", "averykc", "woodshirt", "chemical711", "linkhero", "lord fis0", "hejhejhej", "xvim999", "tazsucks", "colinmacray", "cool123", "zs102", "funkytown27", "croston10", "sexee666x", "jacob7767", "2669331", "adam c73", "bamboozled", "thegoge", "hot123qw", "jrtiger297", "zekerdog1", "damexican99", "1124phc92", "tonganboyz1", "ironman54", "molson 1903", "y8h9i20j", "ketchupp9", "mustard", "shongo3258", "thunderbolt", "zammorak202", "54126635", "sword395", "suxsixdix1", "link 707", "joshua12397", "phenns", "robots100", "luukielakke", "feyenoord", "rangeown1123", "joshienewtro", "fennell100", "bobdylan", "junitre1", "hidare22", "slasher954", "budflip12", "depredad0r", "656565", "shadowzrs", "narut0123", "grimmick15", "gowrangaownz", "ultima 9899", "1337master", "dominzdrac", "xxrunxx34", "sinduko", "baltimore", "machoman275", "y4ou21", "ahmedspy", "ahmed1992", "ronnie19l", "gilplatt97", "evil miedo", "88nst0rm", "ejaeger99", "lilmanas", "oozaru66", "sanctuary", "nija646", "luke1998", "ditto110", "catdog97", "ebfft", "ekonesone", "morten aj", "sootyimpy11", "happyheelas", "schema36", "samyett", "flapula123", "tony634ash12", "thedog77", "ikrissu", "eestimaa", "patrsa", "4po2sj", "danorah", "coolcat60", "crazywiteboi", "g5x74m8ss", "element11123", "25193469", "pomojoona", "seliseli99", "yanumba1boi1", "43gb2im4", "that guy503", "whatsdoin", "glennneke123", "revolution04", "330330", "ryanl809", "asshass1", "gh0stsnyper", "chopper7", "onilink992", "skyrim1234", "emsixteen", "19890910", "taeed24", "assassin12", "vi3t0wnage", "wolfpack450", "lppalscore", "thebob55", "dhuurfa", "roflitsme", "astizzz", "58912b50", "manta566", "koirahullu", "robin5290", "jameswalker", "setiar1", "jesus123", "hej", "abbcab", "smegomeister", "sammson98", "dee alster", "mynameisbob", "schadoxx", "hell4hell", "pk mauzeris", "12211995", "sheepeater9", "knauer12345", "kartunez", "143256a", "machado123", "jayhay22", "jakee bum", "4445poop", "smurfturf248", "jzadams", "arr0wpl0x", "wiirules1", "ryan01234", "donald21", "juoppoeki", "asami789", "joseph999159", "shadow1", "rubini98", "nutella1998", "lazyfaith", "qgy2o6", "mrwreckedem", "soccer1", "ty the ninja", "icychill", "blackeye446", "simonv3", "logiemac11", "maceachern1", "short tastic", "blink182", "pops mahone", "whitebtii", "war eagle47", "mischief", "qazross", "dread46", "13wesley13", "warfight", "hellz rader", "landman", "dakatt13", "nickname", "poop o nator", "0901257", "nummy flame9", "magemage", "ads784", "bellonfire", "werslty", "tyler923", "bowslinger 9", "789987455", "chainmall", "jackson12398", "junkyboy55", "bindoosd", "gaomaster", "xuanhan", "danielmedvec", "123spill", "oddly alive", "newyork", "babababoe", "runskape", "bigdunk69", "soccer13", "cat prince25", "rockman", "paxton269", "22drp88", "uchihazen", "ihateyou111", "expo136", "alexpass", "peliset", "jfas97gv", "sords 4 life", "1011657", "krazy kat575", "tigerflame", "aligindahose", "killforfood", "impzoren", "enter123", "dylan00117", "hotdog10", "bisinji", "rustysam", "tangle golen", "02061996", "jijdwaas", "theelord", "varduka", "apina19", "chinadarkelf", "morrowind", "kickball83", "ilovekayla", "bradwoodward", "brad5420", "kriznar56", "machupichu56", "dezzilisk", "coldocean", "jorieboy3", "lwn13jk", "joneliux458", "5jega55", "gpunks3", "gmschool", "therayman940", "buddyboy", "schulpy", "chelsea1", "madfrk2222", "mmrabb1", "mattiege", "buster98", "monsterdany", "fs7312kp", "eldden", "252 625 26", "grotsnik4", "skulltaker", "peaceweazer", "sta10stik", "jordanator2", "maddydog1", "indomezu", "indasunay8", "xchichimanx", "babychich", "rugissement", "glen123", "slayer78110", "calcetin", "drakeaumann", "eminem45", "voxyvax8563", "konvict", "niklasvegas", "videohai72", "elliotorin", "dadcabsxxx", "cw range", "clanwars", "saynoob", "holland5", "fattyman 12", "77vettee", "tryharder4me", "lolatmyname", "native ta 50", "blood4life", "medevil troy", "humalvelho", "bobbobster6", "aolh8i", "wildboy1888", "9118747", "macacox", "sonny585", "theprorspker", "1a2s3d4f5", "north omaha", "southomaha", "ruindungeon", "fearoth", "gpgard15", "homies01", "kaz123kaz123", "bieniak0048", "myroyalwings", "compact1157", "godawgs08", "dco14", "baddiekins", "gtrhro511", "devilman651", "georgekk9", "zain winters", "158491122", "vovakova", "551879321", "mr0sharkie", "136qwerty136", "tylerrockis1", "yoyobaby", "armadylz x", "wtfyounoob", "fille809", "0763595260", "mansonroks1", "manson1", "dubstepparty", "lmmikjki", "uniwide", "darkmage", "mandude65", "manduderules", "don dalton", "bobutberg", "biscoito77", "15181996", "munkki345", "dfzrbrm7", "zarocks2", "henry6679", "nikdx", "99long", "smilly48", "blackout22", "im not afish", "1wiiwii", "javiercmh", "teno8181", "ancient ttam", "shooter12", "str poiss10", "k2tlin", "segraps", "12qwaszx", "nephew31", "hubbabubba", "rulxman", "singapore", "frozenax7955", "noodles", "terumpk1", "hello101", "xyameax", "bert1127", "scottstimo", "sabop8", "destructo361", "13461618", "khanpop1", "khan2662", "lilboibryan", "benjo171", "spireite1", "king joel", "monkey33", "kukko12", "kukkeli12", "swanepoel101", "luke081295", "mobuis three", "mommys4", "zorzacken", "hookedon", "feetsoup", "forrest11", "lilsaint343", "0akwood", "krisalix", "karadic", "saisseh", "miskaman123", "eckbulldog", "ecknick2510", "cruel guy2", "coolguy2", "jawad1144", "soldier123", "sebber43", "kiwiiscape", "darkshiv", "wert0980", "sp3c m4ul3r", "covcov21", "rhinaboy", "dragon465", "crazy nut45", "9687241", "trx despair", "thethethe123", "x1atcha", "anthony", "mr nart", "epicfail", "marti1101", "redfaction", "paulohare", "m4 0wns", "juska123", "coopershaw14", "13scooter14", "viper snake", "superfluous", "fender love", "devoncallme", "thorax777", "stilton1", "flyflip", "jarrod123", "sircoolio", "zimza toes", "leonard9", "raza838", "deadlove", "jatanoma", "lolnoobs1", "starcat2002", "i6560344", "tarapita", "ud2hnx6q", "xcowboys", "cowboys458", "elgaro12", "83ed04374", "logrono23", "logman23", "grundledib", "m3ath00ks", "1w1ll0wna", "thebrazil", "savtej89", "elkgrove", "darth nome", "5ofclubs", "ultraprono70", "tiili10", "07010701", "u said stick", "ibanez28", "lolxman123", "qwerty123", "rebel goon", "chrome18", "shaunth3pur3", "whatthe", "ruagamer1212", "nothing1", "strownedyou6", "lolnoob", "willroley", "hdw77k69", "shortd00d", "d1014d", "copadon", "girordot", "converse con", "bunny123", "shallowspace", "tums9182", "xxlpad", "arnis1996", "camo124501", "katastrophi4", "wrighton5", "s n x w", "thatiscool", "brusio", "516967853", "audiomonk", "drumstick", "sidekik123", "liban123", "nguyen374", "nguyen1996", "i agsu i", "jsjugb69", "jlventre", "denmarka4", "carltyler1", "megadeth", "batzo", "qwerty88", "crystalfang3", "devin113", "lvl50 attack", "cherrim012", "kristaps288", "9792218", "rockyroller3", "harrytate2", "karrashimo", "never122", "necrovolt", "solovino1", "addpoke", "hpcdrt45", "hotsauce4299", "a1b2c3d4", "elblelbl", "djscratch", "spiderman o", "kabsa23g", "grenade18", "goofygoober", "blueguy8989", "digimon8989", "dusty granny", "naikyaone", "ffs i say no", "erik123", "my str maax", "lukinhas", "tankbuster97", "tanksmash2", "eemuman", "nakkipate", "pk zorbes", "mor1990", "dgfriend5", "456789abc", "twisternick6", "jason22", "alan22589", "gotmoney1", "noob karil3", "ee11ee11", "elmo909", "focus123", "swordfury40", "stonecold9", "rey rey 6116", "metsss11", "lava viper 6", "lavaviper78", "taryn blue1", "taryn71", "pluralgarths", "frederic123", "skeloperch", "zombieperch", "verrrtigo", "karolekas", "fuhq247", "6423694rocky", "bnarney", "fartingfag", "clicker882", "newbie882", "str bonage", "minemunni135", "orangie boi", "jamesd23", "legendy3", "ubernozle1", "maulz w t f2", "lilzoee", "flyman821", "tomtiger647", "fishizle", "hellomoto", "sir hepuli1", "knight alam", "nigahiga", "tors66", "stavar111", "ausdaniel", "bulldogs1", "o he shot me", "fuckhead", "dan 10567", "dragzxasqw", "jeze666", "minarell1", "the a97", "romeydog", "thegringo44", "smdsmd44", "dale 89", "2215077", "chrisroscher", "jagex13245", "rabye str", "hopk1t02", "killangel0", "cheatx101", "grunt628", "asshole1", "wolf boy247", "blompee123", "p k e r 1 0", "mustang1", "99killerbull", "fireman", "jbird197", "jistheman12", "f t f", "hahamines66", "lkjhgfdsa420", "charlie1", "ilovehighlvl", "sedef4", "napplesauce", "tony1243", "biowarrior11", "jmaj6722", "klansman5", "young10", "dawnodarknes", "redalert", "garrdrenn1", "gd2005", "draognzboy", "zippmeup", "oseart55", "starwars501", "xfrightknite", "jlidd8990", "3meister", "dragon13", "madevillemon", "20111993", "cupit11", "harinton7", "xrapidclawx", "jordie4", "aurocks14", "310051552", "dombomagexd", "midweek", "falconfan 24", "battlehero96", "charkrit019", "apple1901", "duongmaster", "johnny07", "recon783", "eraserface", "faxxx1", "nightfall64", "mrjiggles25", "7415001", "zarm3000", "xzsawq67", "final pure g", "zammy88", "rodrigo g m", "daynnight", "mag3 kill4s", "magepwnsu", "prime maggot", "almost1", "elkalibre", "b0n3cl1nkz", "radi100", "kamalkamal1", "i newarcher", "nakniki1", "lord shunoby", "2615948", "stinkerror", "dragondz", "3 run range", "11011997", "robo1rocks", "rocco1998", "tomcat975", "hermano123", "sarlon7", "irish001", "aznballer66", "9312025", "killers455", "matthewmjk", "desparo2222", "yoelmejor", "md650epa1", "gonzalez", "inferno368", "rectamos", "aced52", "iwashere321", "h4x pl0xz0r", "suzukirm85", "jessinator18", "hamedarash", "uzi of doom", "8401397", "sotired", "tootfart64", "alphazone123", "ankouisme123", "daewoo45", "joshowns", "gundymonster", "meow111", "acidacre", "kappa100", "boeman75", "ethsaq2", "yorotomaru", "qwopzxnm7913", "2rubbish", "muslim786", "boomikiller", "daco2023", "knight 988", "wearefamily", "corbo999", "whiterat", "yaranzo5", "keller18", "nick4998", "jnrc3271", "rizeline55", "hradhrad", "lsufreak9", "lsutigers1", "lost pker222", "facebookrule", "tanked you l", "qazwsx123", "spence587", "spencer", "crabs magee", "reaperr7", "kingspell", "969896250", "bewareof me", "200102494g", "grundith", "jadranovo05", "numeris14", "goblinn8", "theepicscout", "sonic44", "phat dood5", "briansucks", "momrocker", "nutmeg60", "sp i r i t", "1heehtam2", "one secret", "firedoor56", "wizcoltt", "merlinnus", "archebowz", "bn0du7cs", "outoftimewon", "snapple12", "gavistire", "098665chaz", "k o sorry", "cristianor7", "masta395", "indy340", "alvaroscary", "killa94", "mr gandolff", "1968camero", "wizkidvic", "uk3dbg", "dgenx21388", "wwefan1994", "printingman", "kopieer", "humper123479", "mikey97", "davydude2", "92thesnack92", "pure0292", "king1234", "1 h8 n3rdz", "bedtime", "caveman422", "ilikepie87", "emosocks1", "5496931", "g2uypie", "enok210", "workrange o", "latefriday", "surplus99", "shabahang", "jonnathan002", "jonny002", "132kickflip", "heelflip", "andrewfly89", "roland123", "snypermatt69", "canucks2012", "youdie111", "1moretime", "dannyhxdman8", "theend1", "g f d", "2fast4you", "zalvose", "assassins", "gangstabros8", "master13", "supanight", "leonard12317", "thefurrymage", "hotrod1994", "tim but dim", "1994tyrer", "woferiukas", "deividas", "failedgull", "goupia123", "inferno 1060", "vtech1060", "junk king33", "junkguy", "racer jaret", "callofduty4", "westorz", "741825963", "zenachue", "bomber357", "kiryu36", "leo8lion", "monk fights", "4991diddly", "boggom 8097", "garret1998", "svensktsnus", "asdaasda123", "ruudinho10", "hans19953109", "eltacolad", "bartlett", "esrb5", "candysex4", "vanquishwolf", "hubertas", "w 3 s c 0 t", "dogtown4life", "10hp clans", "zinedine", "vader11567", "thunder1", "5cripter", "roverdog123", "stallion918", "5489621", "zeroz pure", "alexwars400", "starwars4", "pureskill714", "raheem600", "arromin", "scarm4a1", "ravenangel1", "lancaster", "willheath20", "canuyo2", "smokadadank", "5433285", "blkchocobo", "shalico101", "envious soul", "setcapmoc6", "shepowndu", "sll41ygi", "shoot here2", "triops1029", "bananaboy441", "mieszawski", "pur3iwillbe", "monkeystew", "toddy the pk", "brosis09", "logrono", "gorillazfgi", "b1itz3d", "samsung90", "blakkwizard", "mythodea", "toke223", "hamster223", "guyman123890", "donkeykong", "riduan34", "lekurde", "desiver2", "throw80", "silver gunn", "masteravery", "darkhawk744", "cocoman6", "dragon j86", "1234567890a", "steriili pk", "arsk4", "c robert t", "monster1234", "kiill42", "your1zax", "goliathsnail", "illidan", "balcattaboy", "corbin94", "vereza", "d3susama", "fort 40", "moonlight7", "bobsmith909", "kellyrocks", "idirim", "rennmaus358", "latirem", "muthsera", "pur3man v10", "duckcow1", "kanpar2", "vinimi", "slomdapom", "k8s2e7g4", "hi on 420", "dillweed", "tozzel", "toypaj", "triblouge", "carsa100", "donkie90", "nicolas123", "mag3 pur3 ko", "hayesbonin", "zedukas x", "eligijus", "cbuity", "code515396", "wardmathias", "ritabob", "dubert4", "rooney11", "throwchance", "chance5", "sonderby", "banjomus", "sagar448", "heroes12", "windkid62595", "lilly117", "sly ninja80", "waynegod13", "discombobu99", "rocky34", "zorontu", "xtaylorx", "zezimaki", "ilovelordi", "joey7x", "kaleb8598", "kruiness", "daisymay21", "cow hearder", "kingdrag", "lama 007", "trojans1", "helslayer11", "roblox11", "knifebloody", "1092310923", "dansendeaap", "uiensoep", "pure exz", "theysconator", "ysbrandve1", "chrissy booy", "egghead", "supreme1511", "landis1511", "jdablada", "ugotskills", "kewl kid", "killer99", "nicknack60", "choffee12", "ecmxflawless", "twenty8", "roguewolf33", "firefox101", "kaare83 2", "korsvegen", "da king1295", "xemnas1", "airwalk285", "archman", "sir kieran12", "spiderpig08", "fasterthanyu", "asdflkj", "lihtsaber2", "joe00224", "fortress170", "centurion", "youmeyou4", "laxworld", "xxmrfixitxx", "killer55", "morags", "ericolde", "benno rules", "lotrftw5", "embrach", "meier007", "spike8342", "knuckles", "spit fire469", "tuesday23", "jonahmonk", "blackma9ic", "manmage247", "kingcoke", "paulypauly22", "chickenpoo", "kis a legend", "samtr0n", "amigos39", "blackhole", "abc12pee", "zman1123", "mr frost22", "bubby5522", "lauren maher", "foamy1992", "robertwb69", "rwb691169187", "blackhart74", "sambone", "janreypogi1", "kakilala", "hairy marry", "brucie123", "gaming gypsy", "qwewqwewqwe", "pros only", "killer29043", "night raid89", "hershey", "dragan man62", "sordman", "vaizakhter1", "19692000", "papagal7", "cartof33", "lil 0ctog0n", "hi12", "matthew 1001", "elliemay", "krilitor", "charlesk", "rileymango", "882psauq", "wutknochen", "12muschel78", "ravincal", "warchief1", "joloplex", "scrubs93", "kelly koehn", "goldrush", "0k im sorry", "david123", "zack494", "lima7277", "killermac234", "spartan", "sharmen24", "bigballs", "ricarddragon", "123123s", "illepappeft", "mapet123", "josh6621", "dragon6621", "p 0 0 n3", "silentblue5", "hardflip96", "cbosk8er", "getbuff", "steelbeast97", "ironbeast", "zarkima", "idioteque8", "goselink1989", "janlul123", "rocky1247", "rocklobster9", "supapwnage", "holden123", "vietboihao", "vietboi", "cutvision", "internet", "robert magic", "darlene2", "min ko7", "kristian1998", "adami888", "tetramins", "godssent2", "shadow14", "sawingmotion", "drag00n", "devincm", "harder64", "mrcrowley676", "stevefry", "canadian nrg", "elvens11", "dbl m", "mattrox3", "redgoblin352", "beunard", "benadryl", "old meme", "gepo", "hentai kaid", "gag for it", "vox hugs", "herra huu", "cuscar", "strey", "mednis", "wrap5", "berridge", "leakytaco", "jandy", "vortplex", "laboomz", "colonello", "dumb master", "treynaz", "lampzki", "pulla kahavi", "varduka", "tmdmsk", "chiboubo", "cheesypuffs", "genuinely", "going crazy", "visited", "hyrule zelda", "murrey", "tippett", "lakecake", "scrippycorn", "best quality", "visperi", "two sigma", "holten", "rundera", "reversix", "gurn", "wamp lad", "yas mi", "arab dad", "raft", "hekigan", "meithii", "bewic", "saikkone", "silly", "kurasz", "zaach", "skele", "temptato", "perst", "titacles", "slabbe"));

    private BotPlayer(String text3, String text22, int botMode) {
        super(null);
        this.isBot = true;
        this.botEnabled = true;
        this.botMode = botMode;
        this.setUsername(TextUtil.capitalizeFirst((String)text3));
        this.setNameHash(TextUtil.encodeNameHash(this.getUsername().toLowerCase()));
        this.setPassword(text22);
        this.setSubmittedPassword(text22);
        this.setBotHostAddress("127.0.0.1");
        this.lastRegionChangeMillis = System.currentTimeMillis();
        this.sessionStartMillis = this.lastRegionChangeMillis;
        this.setLoginMagicByte(-1);
        this.setClientBuild(ServerSettings.clientBuild);
        this.setConnectionState(PlayerConnectionState.LOGIN_QUEUED);
        try {
            if (this.loadAndValidateLogin()) {
                this.executeCheatCommand("sbot", null, false);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        ++createdBotCount;
    }

    public final void startTradeAdvertBot() {
        Object value = this;
        World.getTaskScheduler().schedule(new BotTradeAdvertStartTask(this, 2, (BotPlayer)value));
    }

    public final void startSkillingBot() {
        Object value = this;
        World.getTaskScheduler().schedule(new BotTaskSelectionTask(this, 2, (BotPlayer)value));
    }

    public final void startProgressiveBot() {
        Object value = this;
        World.getTaskScheduler().schedule(new BotTaskPlanningTask(this, 2, (BotPlayer)value));
    }

    public final void startCombatLoadoutBot() {
        Object value = this;
        World.getTaskScheduler().schedule(new BotCombatLoadoutTask(this, 2, (BotPlayer)value));
    }

    public final void startDropPartyBot() {
        Object value = this;
        dropPartyBots.add(this);
        World.getTaskScheduler().schedule(new DropPartyBotHideTask(this, 2, (BotPlayer)value));
    }

    public final void startMinigameBot() {
        MinigameBotManager.startMinigameBot(this);
        World.getTaskScheduler().schedule(new MinigameBotJoinTask(2, this));
    }

    public final void startClanWarsBot(int value2) {
        BotPlayer botPlayer = this;
        this.clanWarsBot = true;
        if (value2 == 5) {
            this.clanWarsTeamId = 1;
            ClanWarsBotManager.clanWarsTeamOneBots.add(this);
        } else {
            this.clanWarsTeamId = 2;
            ClanWarsBotManager.clanWarsTeamTwoBots.add(this);
        }
        ClanWarsBotHideTask clanWarsBotHideTask = new ClanWarsBotHideTask(this, 2, botPlayer);
        World.getTaskScheduler().schedule(clanWarsBotHideTask);
    }

    public static void createBotFromPool(int value3, String text3, int value22) {
        String text2 = (String)botNamePool.get(value3);
        if (shuffledBotNamePool.size() > 0) {
            text2 = (String)shuffledBotNamePool.get(value3);
        }
        if (value22 == 4 && progressiveBotNameQueue.size() > 0) {
            text2 = (String)progressiveBotNameQueue.get(0);
            progressiveBotNameQueue.remove(0);
        }
        new BotPlayer(text2, text3, value22);
    }

    public static void createNamedBot(String text3, String text22, int value2) {
        new BotPlayer(text3, text22, value2);
    }

    @Override
    public final void initializeNewPlayer() {
        this.moveTo(new Position(ServerSettings.respawnX, ServerSettings.respawnY, ServerSettings.respawnPlane));
        this.randomizeAppearance();
        this.completeTutorial();
        this.completeAllQuestStates();
        BotPlayer botPlayer = this;
        botPlayer.packetSender.closeInterfaces();
    }

    public static void prepareBotNamePools() {
        Collections.shuffle(botNamePool);
        Collections.shuffle(defaultProgressiveBotNames);
        progressiveBotNameQueue.addAll(defaultProgressiveBotNames);
    }

    public static void removeConfiguredBotNames() {
        for (Object removedBotNameObject : removedBotNames) {
            String text = (String)removedBotNameObject;
            if (botNamePool.contains(text)) {
                botNamePool.remove(text);
            }
            if (!defaultProgressiveBotNames.contains(text)) continue;
            defaultProgressiveBotNames.remove(text);
        }
    }

    public static void loadExistingProgressiveBotNames() {
        shuffledBotNamePool.addAll(botNamePool);
        Collections.shuffle(shuffledBotNamePool);
        Object value = new File("./data/characters/");
        File[] fileArray = ((File)value).listFiles();
        if (fileArray == null) {
            return;
        }
        int length = fileArray.length;
        int index = 0;
        while (index < length) {
            value = fileArray[index];
            if (progressiveBotNameQueue.size() >= ServerSettings.progressiveBotCount) break;
            if (botNamePool.contains(value = CharacterFileManager.stripFileExtension(((File)value).getName()).toLowerCase()) && progressiveBotNameQueue.size() < ServerSettings.progressiveBotCount) {
                progressiveBotNameQueue.add(value);
                shuffledBotNamePool.remove(value);
            }
            ++index;
        }
    }
}
