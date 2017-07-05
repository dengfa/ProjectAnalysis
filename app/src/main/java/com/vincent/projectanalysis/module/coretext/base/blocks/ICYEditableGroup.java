//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.vincent.projectanalysis.module.coretext.base.blocks;

import java.util.List;

public interface ICYEditableGroup {
    ICYEditable findEditable(float var1, float var2);

    ICYEditable getFocusEditable();

    ICYEditable findEditableByTabId(int var1);

    List<ICYEditable> findAllEditable();
}
