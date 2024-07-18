package org.deslre;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Pic {
    public static final char SPACE = ' ';
    public static final char SPACE_6PE = '\u2006';
    public static final char SPACE_THIN = '\u2009';
    public static final char SPACE_BRAILLE = '\u2800';
    public static final char SPACE_HAIR = '\u200a';
    public static final char SINGLE_BULL_00 = '⠁';
    public static final char SINGLE_BULL_01 = '⠈';
    public static final char SINGLE_BULL_10 = '⠂';
    public static final char SINGLE_BULL_11 = '⠐';
    public static final char SINGLE_BULL_20 = '⠄';
    public static final char SINGLE_BULL_21 = '⠠';
    public static final char SINGLE_BULL_30 = '⡀';
    public static final char SINGLE_BULL_31 = '⢀';
    public static final char FULL_BULL = '⣿';
    /* ↑ 定义部分字符以便用户调用 ↑ */

    private static final char[] C;
    private static final Map<Character, Character> RC = new HashMap<>();
    private static final char TEMP_CHAR = '\u0000';
    private static final String O_D_SPACE = String.valueOf(new char[]{SINGLE_BULL_20});
    private static String default_space = O_D_SPACE;

    static {
        C = new char[]{
                TEMP_CHAR, '⠁', '⠂', '⠃', '⠄', '⠅', '⠆', '⠇', '⠈', '⠉', '⠊', '⠋', '⠌', '⠍', '⠎', '⠏',
                '⠐', '⠑', '⠒', '⠓', '⠔', '⠕', '⠖', '⠗', '⠘', '⠙', '⠚', '⠛', '⠜', '⠝', '⠞', '⠟',
                '⠠', '⠡', '⠢', '⠣', '⠤', '⠥', '⠦', '⠧', '⠨', '⠩', '⠪', '⠫', '⠬', '⠭', '⠮', '⠯',
                '⠰', '⠱', '⠲', '⠳', '⠴', '⠵', '⠶', '⠷', '⠸', '⠹', '⠺', '⠻', '⠼', '⠽', '⠾', '⠿',
                '⡀', '⡁', '⡂', '⡃', '⡄', '⡅', '⡆', '⡇', '⡈', '⡉', '⡊', '⡋', '⡌', '⡍', '⡎', '⡏',
                '⡐', '⡑', '⡒', '⡓', '⡔', '⡕', '⡖', '⡗', '⡘', '⡙', '⡚', '⡛', '⡜', '⡝', '⡞', '⡟',
                '⡠', '⡡', '⡢', '⡣', '⡤', '⡥', '⡦', '⡧', '⡨', '⡩', '⡪', '⡫', '⡬', '⡭', '⡮', '⡯',
                '⡰', '⡱', '⡲', '⡳', '⡴', '⡵', '⡶', '⡷', '⡸', '⡹', '⡺', '⡻', '⡼', '⡽', '⡾', '⡿',
                '⢀', '⢁', '⢂', '⢃', '⢄', '⢅', '⢆', '⢇', '⢈', '⢉', '⢊', '⢋', '⢌', '⢍', '⢎', '⢏',
                '⢐', '⢑', '⢒', '⢓', '⢔', '⢕', '⢖', '⢗', '⢘', '⢙', '⢚', '⢛', '⢜', '⢝', '⢞', '⢟',
                '⢠', '⢡', '⢢', '⢣', '⢤', '⢥', '⢦', '⢧', '⢨', '⢩', '⢪', '⢫', '⢬', '⢭', '⢮', '⢯',
                '⢰', '⢱', '⢲', '⢳', '⢴', '⢵', '⢶', '⢷', '⢸', '⢹', '⢺', '⢻', '⢼', '⢽', '⢾', '⢿',
                '⣀', '⣁', '⣂', '⣃', '⣄', '⣅', '⣆', '⣇', '⣈', '⣉', '⣊', '⣋', '⣌', '⣍', '⣎', '⣏',
                '⣐', '⣑', '⣒', '⣓', '⣔', '⣕', '⣖', '⣗', '⣘', '⣙', '⣚', '⣛', '⣜', '⣝', '⣞', '⣟',
                '⣠', '⣡', '⣢', '⣣', '⣤', '⣥', '⣦', '⣧', '⣨', '⣩', '⣪', '⣫', '⣬', '⣭', '⣮', '⣯',
                '⣰', '⣱', '⣲', '⣳', '⣴', '⣵', '⣶', '⣷', '⣸', '⣹', '⣺', '⣻', '⣼', '⣽', '⣾', '⣿'
        };
        for (int i = 0; i < C.length; i++) RC.put(C[i], C[0b11111111 ^ i]);
        RC.put('\n', '\n');
    }

    public static String getDefaultSpace() {
        return Pic.default_space;
    }

    public static Pic create(String path) {
        return create(path, false, Pic.default_space);
    }

    public static Pic create(String path, String space) {
        return create(path, false, space);
    }

    public static Pic create(String path, boolean anti) {
        return create(path, anti, Pic.default_space);
    }

    public static Pic create(String path, boolean anti, String space) {
        File file = new File(path);
        if (space == null || space.isEmpty()) return create(file, anti, Pic.default_space);
        return create(file, anti, space);
    }

    public static Pic create(File path) {
        return create(path, false, Pic.default_space);
    }

    public static Pic create(File path, String space) {
        return create(path, false, space);
    }

    public static Pic create(File path, boolean anti) {
        return create(path, anti, Pic.default_space);
    }

    public static Pic create(File path, boolean anti, String space) {
        try {
            BufferedImage bf = ImageIO.read(path);
            int[][] data = new int[bf.getHeight()][bf.getWidth()];
            for (int y = 0; y < data.length; y++)
                for (int x = 0; x < data[y].length; x++)
                    data[y][x] = getVal(bf.getRGB(x, y));
            if (space == null || space.length() == 0) return create(data, anti, Pic.default_space);
            return create(data, anti, space);
        } catch (Exception e) {
            e.printStackTrace();
            return buildPic("", Pic.default_space);
        }
    }

    private static Pic create(int[][] data, boolean anti, String space) {
        StringBuilder sb = new StringBuilder();
        int flag = anti ? 1 : 0;
        for (int y = 0; y < data.length / 4; y++) {
            for (int x = 0; x < data[0].length / 2; x++) {
                int curr = 0, add = 1, yc = 4 * y, xc = x * 2;
                for (int xt = 0; xt < 2; xt++)
                    for (int yt = 0; yt < 3; yt++) {
                        if (data[yc + yt][xc + xt] == flag) curr += add;
                        add <<= 1;
                    }
                for (int xt = 0; xt < 2; xt++) {
                    if (data[yc + 3][xc + xt] == flag) curr += add;
                    add <<= 1;
                }
                sb.append(C[curr]);
            }
            sb.append("\n");
        }
        return buildPic(sb.toString().replace(String.valueOf(TEMP_CHAR), space), space);
    }

    private static int getVal(int n) {
        int res = 0;
        res += (n & 0b111111110000000000000000) >> 16;
        res += (n & 0b1111111100000000) >> 8;
        res += (n & 0b11111111);
        if (res > 350) return 1;
        return 0;
    }

    public static String reverse(String pic) {
        return reverse(pic, Pic.default_space);
    }

    private static String reverse(String pic, String space) {
        if (pic == null) return null;
        if (space == null || space.length() == 0) return reverse(pic, Pic.default_space);
        StringBuilder sb = new StringBuilder();
        for (char c : pic.replace(space, String.valueOf(TEMP_CHAR)).toCharArray())
            sb.append(RC.getOrDefault(c, c));
        return sb.toString().replace(String.valueOf(TEMP_CHAR), space);
    }

    public static String toSafe(String pic) {
        return toSafe_D(pic, String.valueOf(SINGLE_BULL_20));
    }

    public static String toSafe_D(String pic, char... replace) {
        if (replace == null || replace.length == 0) return toSafe_D(pic, String.valueOf(SINGLE_BULL_20));
        return toSafe_D(pic, String.valueOf(replace));
    }

    public static String toSafe_D(String pic, String replace) {
        if (pic == null) return null;
        if (replace == null || replace.isEmpty()) return toSafe_D(pic, String.valueOf(SINGLE_BULL_20));
        return pic.replace(Pic.default_space, replace);
    }

    public static String toNormal(String pic) {
        if (pic == null) return null;
        return toSafe_D(pic, String.valueOf(SPACE_BRAILLE));
    }

    public static String changeDefaultSpace(String spaces) {
        if (spaces == null || spaces.isEmpty())
            return changeDefaultSpace(O_D_SPACE);
        String old = Pic.default_space;
        Pic.default_space = spaces;
        return old;
    }

    public static String changeDefaultSpace(char... spaces) {
        if (spaces == null || spaces.length == 0) return changeDefaultSpace(O_D_SPACE);
        return changeDefaultSpace(new String(spaces));
    }

    private static Pic buildPic(String pic, String space) {
        Pic res = new Pic();
        res.currentPicture = pic;
        res.space = space;
        return res;
    }


    private String currentPicture = "";
    private String space = Pic.default_space;
    private boolean changeable = false;

    private Pic() {
    }

    public Pic(Pic another) {
        this.currentPicture = another.currentPicture;
        this.space = another.space;
        this.changeable = another.changeable;
    }

    public Pic(File path) {
        this(path, false, Pic.default_space);
    }

    public Pic(File path, boolean anti) {
        this(path, anti, Pic.default_space);
    }

    public Pic(File path, String space) {
        this(path, false, space);
    }

    public Pic(File path, boolean anti, String space) {
        this.currentPicture = create(path, anti, space).currentPicture;
        this.space = space;
    }

    public Pic(String pic) {
        this(pic, Pic.default_space);
    }

    public Pic(String pic, char... spaces) {
        if (spaces == null || spaces.length == 0) this.space = Pic.default_space;
        else this.space = String.valueOf(spaces);
        this.currentPicture = Pic.initialPictureString(pic, this.space);
    }

    public Pic(String pic, String space) {
        if (space == null || space.isEmpty()) this.space = Pic.default_space;
        else this.space = space;
        this.currentPicture = Pic.initialPictureString(pic, this.space);
    }

    private static String initialPictureString(String pic, String space) {
        if (pic == null) return "";
        StringBuilder sb = new StringBuilder();
        boolean out = false;
        for (char c : pic.replace(space, String.valueOf(TEMP_CHAR)).toCharArray()) {
            if (RC.containsKey(c)) sb.append(c);
            else {
                sb.append(TEMP_CHAR);
                out = true;
            }
        }
        String res = sb.toString().replace(String.valueOf(TEMP_CHAR), space);
        if (out) new RuntimeException("PictureString contains undefined character. SpaceCharacters ["
                + space + "] have took place of them.").printStackTrace();
        return res;
    }

    public Pic toSafe() {
        return this.toSafe_D(String.valueOf(SINGLE_BULL_20));
    }

    public Pic toSafe_D() {
        return this.toSafe_D(String.valueOf(SINGLE_BULL_20));
    }

    public Pic toSafe_D(char... replace) {
        if (replace == null || replace.length == 0) return this.toSafe_D(String.valueOf(Pic.SINGLE_BULL_20));
        return this.toSafe_D(String.valueOf(replace));
    }

    public Pic toSafe_D(String replace) {
        if (replace == null || replace.length() == 0) return toSafe_D(String.valueOf(Pic.SINGLE_BULL_20));
        return getReturn(this.currentPicture.replace(this.space, replace), this.space);
    }

    public Pic toNormal() {
        return this.toSafe_D(String.valueOf(SPACE_BRAILLE));
    }

    public Pic reverse() {
        return getReturn(Pic.reverse(this.currentPicture, this.space), this.space);
    }

    public Pic changeSpace(char... newSpaces) {
        if (newSpaces == null || newSpaces.length == 0) return changeSpace(O_D_SPACE);
        return changeSpace(String.valueOf(newSpaces));
    }

    public Pic changeSpace(String newSpace) {
        if (newSpace == null || newSpace.isEmpty()) return changeSpace(O_D_SPACE);
        return getReturn(this.currentPicture.replace(this.space, newSpace), newSpace);
    }

    public String getSpace() {
        return this.space;
    }

    public Pic reCreate(File path) {
        return reCreate(path, false);
    }

    public Pic reCreate(File path, boolean anti) {
        return getReturn(create(path, anti, this.space).currentPicture, this.space);
    }

    public Pic reCreate(String pic) {
        return getReturn(initialPictureString(pic, this.space), this.space);
    }

    public Pic changeable() {
        return setChangeable(true);
    }

    public Pic unchangeable() {
        return setChangeable(false);
    }

    public Pic setChangeable(boolean changeable) {
        this.changeable = changeable;
        return this;
    }

    private Pic getReturn(String pic, String space) {
        if (this.changeable) {
            this.currentPicture = pic;
            return this;
        }
        return Pic.buildPic(pic, space);
    }

    @Override
    public String toString() {
        return this.currentPicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Pic pic = (Pic) o;
        return this.changeable == pic.changeable &&
                Objects.equals(this.currentPicture, pic.currentPicture) &&
                Objects.equals(this.space, pic.space);
    }

    @Override
    public int hashCode() {
        return this.currentPicture.hashCode();
    }
}