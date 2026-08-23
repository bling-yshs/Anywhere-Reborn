// IOverlayService.aidl
package com.yshs.anywhere_.services.overlay;
import com.yshs.anywhere_.model.database.AnywhereEntity;

// Declare any non-default types here with import statements

interface IOverlayService {
    void addOverlay(in AnywhereEntity entity);
    void closeOverlay(in AnywhereEntity entity);
}
