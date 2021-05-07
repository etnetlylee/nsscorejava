package com.etnet.coresdk.nssCoreService;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AppContext {
//    final asaLoadCompleted = BehaviorSubject<bool>.seeded(false);
    final BehaviorSubject<Boolean> asaLoadCompleted = BehaviorSubject.create();
}
