package com.ysxsoft.common_base.view.custom.image.group.region;

import android.graphics.Region;

public interface IRegionManager {
    Region[] calculateRegion(int size, int subSize, int gap, int count);
}
