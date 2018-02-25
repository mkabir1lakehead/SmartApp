__metaclass__ = type
import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestClassifier
from sklearn.grid_search import GridSearchCV
from sklearn import cross_validation, metrics,preprocessing, datasets,tree
import matplotlib.pylab as plt
from itertools import product
from IPython.display import Image
import pydotplus
import os
# inport data, check the rate of data
column_names = ["action-id", "mean","median","max","min","std","var","dc","maxfp","energy"]
target = "action-id"
train = pd.read_csv('./OrigenalData/data_RandomForest.txt',header=None, names=column_names)
train['action-id'].value_counts()
k = 10
subject = pd.DataFrame(train["action-id"].value_counts())
subject.index.name = ['Subject']
print(subject.head(k))
#features and lables
x_columns = [x for x in train.columns if x not in [target]]
X = train[x_columns]
y = train['action-id']
print(X)
rf0 = RandomForestClassifier(oob_score=True, random_state=10)
rf0.fit(X,y)
#print("rf0:",rf0.oob_score_)
#print(type(y))


y = preprocessing.label_binarize(y, classes=[0, 1, 2, 3, 4, 5])

'''y_predprob1 = rf0.predict_proba(X)

y_ture = list(y)
print(y_ture)
y_pre = []
for pre in y_predprob1:
    lable = list(pre).index(np.amax(pre))
    y_pre.append(lable)
print("AUC Score (Train): %f" % metrics.roc_auc_score(y_ture,y_pre))
'''
#the best n_estimators is 140
'''param_test1= {'n_estimators':list(range(10,211,10))}
gsearch1= GridSearchCV(estimator = RandomForestClassifier(min_samples_split=100,min_samples_leaf=20,
                                max_depth=8,max_features='sqrt' ,random_state=10),
                                param_grid =param_test1, scoring='roc_auc',cv=5)
y = preprocessing.label_binarize(y, classes=[0, 1, 2, 3, 4, 5])
gsearch1.fit(X,y)
#for element in gsearch1.grid_scores_,gsearch1.best_params_, gsearch1.best_score_:
#    print(element)

# the best max_depth = 13, the min_samples_split = 70?not sure
param_test2 = {'max_depth':list(range(3,14,2)), 'min_samples_split':list(range(50,201,20))}
gsearch2 = GridSearchCV(estimator = RandomForestClassifier(n_estimators= 60,
                                  min_samples_leaf=20,max_features='sqrt' ,oob_score=True, random_state=10),
   param_grid = param_test2, scoring='roc_auc',iid=False, cv=5)
gsearch2.fit(X,y)
#for element in gsearch2.grid_scores_, gsearch2.best_params_, gsearch2.best_score_:
#    print(element)

#min_sample_split = 2, min_sample_leaf = 20
param_test3 = {'min_samples_split':list(range(10,21,5)), 'min_samples_leaf':list(range(15,21,5))}
gsearch3 = GridSearchCV(estimator = RandomForestClassifier(n_estimators= 140, max_depth=13,
                                  max_features='sqrt' ,oob_score=True, random_state=10),
   param_grid = param_test3, scoring='roc_auc',iid=False, cv=5)
gsearch3.fit(X,y)
print(gsearch3.grid_scores_, gsearch3.best_params_, gsearch3.best_score_)
''‘
#max_feauures = 4
param_test4 = {'max_features':list(range(1,5,1))}
gsearch4 = GridSearchCV(estimator = RandomForestClassifier(n_estimators= 140, max_depth=13, min_samples_split=10,
                                  min_samples_leaf=20 ,oob_score=True, random_state=10),
   param_grid = param_test4, scoring='roc_auc',iid=False, cv=5)
gsearch4.fit(X,y)
for element in gsearch4.grid_scores_, gsearch4.best_params_, gsearch4.best_score_:
    print(element)
'''

rf1 = RandomForestClassifier(n_estimators= 140, max_depth=13, min_samples_split=2,
                                  min_samples_leaf=20,max_features=4 ,oob_score=True, random_state=10)
rf1.fit(X,y)
print("rf1: ",rf1.oob_score_)
x1 = np.array([10.033908611642865],[10.022726417034939],[10.733956097750621],[9.804244031583053],
      [0.23313136099102263],[0.05435023147752651],[20.024681680283656],[9.8],[580.475306192771]).reshape(-1,1)
print("predict example:",rf1.apply(x1))



