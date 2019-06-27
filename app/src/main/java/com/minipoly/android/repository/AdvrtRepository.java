package com.minipoly.android.repository;

import com.minipoly.android.entity.Advrt;
import com.minipoly.android.livedata.FireLiveDocument;

import static com.minipoly.android.References.realestates;

public class AdvrtRepository {

    public static FireLiveDocument addAdvrt(Advrt advrt){
        String id = realestates.document().getId();
        advrt.setId(id);
        return new FireLiveDocument(realestates.add(advrt), Advrt.class);
    }

}
