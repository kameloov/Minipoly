package com.minipoly.android.param_managers;

import androidx.lifecycle.MutableLiveData;

import com.minipoly.android.R;
import com.minipoly.android.entity.ComputerInfo;
import com.minipoly.android.entity.ComputerMisc;
import com.minipoly.android.entity.CustomRadio;
import com.minipoly.android.entity.Toe;
import com.minipoly.android.livedata.FireLiveDocument;
import com.minipoly.android.num.ToeType;
import com.minipoly.android.repository.MiscRepository;

import java.util.ArrayList;
import java.util.List;

public class ComputerManager {
    private ComputerInfo info = new ComputerInfo();
    public FireLiveDocument<ComputerMisc> misc = MiscRepository.getComputerMisc();
    private MutableLiveData<Integer> screenIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> graphicIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> StorageIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> processorIndex = new MutableLiveData<>(0);
    private MutableLiveData<Integer> ramIndex = new MutableLiveData<>(0);
    public MutableLiveData<CustomRadio> ssd = new MutableLiveData<>(new CustomRadio(false, "Yes", "No"));
    public MutableLiveData<Integer> getScreenIndex() {
        return screenIndex;
    }


    public static List<String> getTags(ComputerInfo computerInfo) {
        ArrayList<String> list = new ArrayList<>();
        if (computerInfo != null) {
            if (computerInfo.getProcessor() != null)
                list.add(computerInfo.getProcessor());
            if (computerInfo.getScreenSize() > 0)
                list.add(computerInfo.getScreenSize() + " inch");
            if (computerInfo.getGraphic() > 0)
                list.add(computerInfo.getGraphic() + " graphic");
            if (computerInfo.getRam() > 0)
                list.add(computerInfo.getRam() + " GB Ram");
            if (computerInfo.getStorage() > 0)
                list.add(computerInfo.getStorage() + "GB storage");
            if (computerInfo.isSsd())
                list.add("SSD");
        }
        if (list.size() < 7) {
            for (int i = 0; i <= 7 - list.size(); i++)
                list.add(null);
        }
        return list;
    }

    public static List<Toe> getToes(ComputerInfo info, ToeType toeType) {
        ArrayList<Toe> lst = new ArrayList<>();
        if (info != null) {
            lst.add(new Toe(info.getScreenSize() + "", R.drawable.ic_screen, toeType));
            lst.add(new Toe(info.getRam() + " G", R.drawable.ic_ram_pc, toeType));
            lst.add(new Toe(info.getStorage() + "G", R.drawable.ic_sd_storage, toeType));
        } else {
            lst.add(null);
            lst.add(null);
            lst.add(null);
        }

        return lst;
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

    public ComputerInfo getInfo() {
        info.setSsd(ssd.getValue().isChecked());
        return info;
    }
}
