package com.minipoly.android.param_managers;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.entity.ComputerInfo;
import com.minipoly.android.entity.ComputerMisc;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.repository.MiscRepository;

public class ComputerManager {
    private ComputerInfo info = new ComputerInfo();
    public FireLiveDocument<ComputerMisc> misc = MiscRepository.getComputerMisc();
    private MutableLiveData<Integer> screenIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> graphicIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> StorageIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> processorIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> ramIndex = new MutableLiveData<>(0);

    public MutableLiveData<Integer> getScreenIndex() {
        return screenIndex;
    }

    public void setScreenIndex(Integer screenIndex) {
        this.screenIndex.setValue(screenIndex);
        this.info.setScreenSize(misc.getValue().getScreen().get(screenIndex));
    }

    public MutableLiveData<Integer> getGraphicIndex() {
        return graphicIndex;
    }

    public void setGraphicIndex(Integer graphicIndex) {
        this.graphicIndex.setValue(graphicIndex);
        info.setGraphic(Integer.parseInt(misc.getValue().getGraphic().get(graphicIndex)));
    }

    public MutableLiveData<Integer> getStorageIndex() {
        return StorageIndex;
    }

    public void setStorageIndex(Integer storageIndex) {
        StorageIndex.setValue(storageIndex);
        info.setStorage(misc.getValue().getStorage().get(storageIndex));
    }

    public MutableLiveData<Integer> getProcessorIndex() {
        return processorIndex;
    }

    public void setProcessorIndex(Integer processorIndex) {
        this.processorIndex.setValue(processorIndex);
        info.setProcessor(misc.getValue().getProcessor().get(processorIndex));
    }

    public MutableLiveData<Integer> getRamIndex() {
        return ramIndex;
    }

    public void setRamIndex(Integer ramIndex) {
        this.ramIndex.setValue(ramIndex);
        info.setRam(misc.getValue().getRam().get(ramIndex));
    }
}
