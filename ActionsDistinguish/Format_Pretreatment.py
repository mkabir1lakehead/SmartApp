import ast
import math
import numpy as np
import pylab as pl
import scipy.signal as signal

FEATURE = ("mean","median","max","min","std","var","dc","energy","maxfp")
STATUS = ("Sitting","Walking","Upstairs","Downstairs","Jogging","Standing")#static,sleeping

def preprocess(fir_dir, Seg_granularity):
    gravity_data = []
    with open(fir_dir) as f:
        index = 0
        for line in f:
            clear_line = line.lstrip().rstrip(';\n').rstrip(',')
            #print(clear_line)
            raw_list = clear_line.split(',')
            index +=1
            if len(raw_list) != 6:
                continue
            status = raw_list[1]
            acc_x = float(raw_list[3])
            acc_y = float(raw_list[4])
            acc_z = float(raw_list[5])
            if acc_x == 0 or acc_y == 0 or acc_z == 0:
                continue
            gravity = math.sqrt(math.pow(acc_x,2)+math.pow(acc_y,2)+math.pow(acc_z,2))
            gravity_tuple = {"gravity":gravity, "status":status}
            gravity_data.append(gravity_tuple)

    #split data sample of gravity
    split_data = []
    cur_cluster = []
    counter = 0
    last_status = gravity_data[0]["status"]
    for gravity_tuple in gravity_data:
        if not (counter < Seg_granularity and gravity_tuple["status"] == last_status):
            if counter >= Seg_granularity:
                seg_data = {"status":last_status,"values":cur_cluster}
                split_data.append(seg_data)
            cur_cluster = []
            counter = 0
        cur_cluster.append(gravity_tuple["gravity"])
        last_status = gravity_tuple["status"]
        counter +=1
    # compute statistics of gravity data

    sampling_rate = 20
    plotnum = 0

    statistics_data = []
    for seg_data in split_data:

        np_values = np.array(seg_data.pop("values"))
        fft_size = len(np_values)
        xs = np_values[:fft_size]* signal.hann(fft_size, sym=0)*2
        t = np.arange(0,float(fft_size/sampling_rate),1.0/sampling_rate)
        xf = np.fft.rfft(xs/fft_size)
        freqs = np.linspace(0,sampling_rate/2, fft_size/ 2 + 1)
        xfp = 20 * np.log10(np.clip(np.abs(xf), 1e-20, 1e100))
        dc = xfp[0]
        maxfp = list(xfp).index(np.amax(xfp[2:]))/(fft_size/sampling_rate)
        xfpsqure = xfp * xfp
        area = np.sum(xfpsqure) * (sampling_rate / 2) / (fft_size / 2)

        '''if plotnum == 1:
            pl.figure(figsize=(8, 4))
            pl.subplot(2,1,1)
            pl.plot(t[:fft_size], xs)
            pl.xlabel(u"time(sec)")
            pl.title(u"shape-time-frequency")
            pl.subplot(2,1,2)
            pl.plot(freqs, xfp)
            pl.xlabel(u"frequency(Hz) & xfp[0] = %f & energy = %f" %(dc, area))
            pl.subplots_adjust(hspace=0.4)
            pl.show()
            pl.plot(sampling_rate,xfp)'''


        seg_data["max"] = np.amax(np_values)
        seg_data["min"] = np.amin(np_values)
        #seg_data["ptp"] = np.ptp(np_values)
        seg_data["std"] = np.std(np_values)
        seg_data["mean"] = np.mean(np_values)
        seg_data["median"] = np.median(np_values)
        seg_data["var"] = np.var(np_values)

        seg_data["dc"] = dc
        seg_data["maxfp"] = maxfp
        seg_data["energy"] = area

        statistics_data.append(seg_data)
        plotnum += 1
    #write statistics result into a file in format of lable ,mean, max, min, std
    with open("./OrigenalData/data_RandomForest.txt", "a") as the_file:
        for seg_data in statistics_data:
            '''row = str(STATUS.index(seg_data["status"])) + " " + \
            str(FEATURE.index("mean")) + ":" + str(seg_data["mean"]) + " " + \
            str(FEATURE.index("max")) + ":" + str(seg_data["max"]) + " " + \
            str(FEATURE.index("min")) + ":" + str(seg_data["min"]) + " " + \
            str(FEATURE.index("std")) + ":" + str(seg_data["std"]) + " " + \
            str(FEATURE.index("var")) + ":" + str(seg_data["var"]) + " " + \
            str(FEATURE.index("dc")) + ":" + str(seg_data["dc"]) + " " + \
            str(FEATURE.index("energy")) + ":" + str(seg_data["energy"]) + "\n"'''
            row = str(STATUS.index(seg_data["status"])) + "," + \
            str(seg_data["mean"]) + "," + \
            str(seg_data["median"]) + "," + \
            str(seg_data["max"]) + "," + \
            str(seg_data["min"]) + "," + \
            str(seg_data["std"]) + "," + \
            str(seg_data["var"]) + "," + \
            str(seg_data["dc"]) + "," + \
            str(seg_data["maxfp"]) + "," + \
            str(seg_data["energy"]) + "\n"

            print(row)
            the_file.write(row)
        print("sample numbers = " , len(statistics_data))

if __name__ == "__main__":
    preprocess("./OrigenalData/WISDM_ar_v1.1_raw.txt",100)
    pass