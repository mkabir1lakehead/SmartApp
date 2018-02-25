import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import os
import re
from sklearn.metrics import precision_score, recall_score, f1_score, confusion_matrix, roc_curve, auc

if __name__ == "__main__":
    relAddress = os.getcwd()
    print(relAddress)
    #delete the ';' of the end of lien
    '''
    pat = r'([a-zA-Z0-9])*;$'
    with open('./OrigenalData/WISDM_ar_v1.1_raw.txt', 'r') as f1:
        print('Normally Opens DataDocument')
        line = f1.readline()
        if re.search(pat, line):
            print('It is Not CSV_Form')
            f1.seek(0)
            fileUpdate = open('./OrigenalData/WISDM_ar_v1.1_raw_Update1.txt', 'w')
            for line in f1:
                fileUpdate.write("".join(line.split(';')))
            fileUpdate.close()

    f1 = open('./OrigenalData/WISDM_ar_v1.1_raw.txt', 'r')
    if re.search(pat, f1.readline()):
        f1.close()
        f1 = open('./OrigenalData/WISDM_ar_v1.1_raw.txt', 'w')
        fileUpdate = open('./OrigenalData/WISDM_ar_v1.1_raw_Update1.txt', 'r')
        for line in fileUpdate:
            f1.write(line)
        f1.close()
        fileUpdate.close()
    f1.close()
    '''
    column_names = ['user-id', 'activity', 'timestamp', 'x-axis', 'y-axis', 'z-axis']
    df = pd.read_csv('./OrigenalData/WISDM_ar_v1.1_raw.txt',header=None, names=column_names)
    k = 10
    print(df.head(k))
    subject = pd.DataFrame(df["user-id"].value_counts())
    subject.index.name = ['Subject']
    print(subject.head(k))
    activities = pd.DataFrame(df["activity"].value_counts())
    activities.index.names = ['Activity']
    print(activities.head(k))
    activity_of_subjects = pd.DataFrame(df.groupby("user-id")["activity"].value_counts())
    print(activity_of_subjects.unstack().head(k))
    activity_of_subjects.unstack().plot(kind='bar', stacked=True, colormap='Blues', title="Distribution")
    plt.show()
    activities.unstack().plot(kind = 'pie',stacked=True, colors=['red','orange','yellow','green','blue'], title="Distribution", autopct = '%.f%%')
    plt.show()