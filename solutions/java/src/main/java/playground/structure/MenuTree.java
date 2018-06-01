package playground.structure;

import java.util.List;

public class MenuTree {

    public Dir root;

    private class Dir {

        String path;

        List<String> files;

        List<Dir> dirs;
    }
}
