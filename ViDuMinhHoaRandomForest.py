import pandas as pd
import numpy as np
from scipy.stats import mode
from sklearn.tree import export_text
from sklearn.tree import DecisionTreeClassifier
def bootstrapping(train_df, n_bootstrap):
    bootstrap_indices = np.random.randint(low=0, high=len(train_df), size=n_bootstrap)
    df_bootstrapped = train_df.iloc[bootstrap_indices]
    
    return df_bootstrapped
df = pd.read_csv("data.csv")
df = df.drop(columns="id",axis=1)
print(df)
print("====================================")
save=[]
for i in range(0,4):
    print("Cây số: ",end="")
    print(i+1)
    n_bootstrap,_ = df.shape
    x=bootstrapping(df,n_bootstrap)
    print(x)
    X_train=x.iloc[:,0:5]
    Y_train=x.iloc[:,-1]
    tree=DecisionTreeClassifier(max_depth=3,max_features='sqrt' , random_state=42, criterion='entropy')
    tree.fit(X_train,Y_train)
    save.append(tree)
    text = export_text(tree, feature_names=["x0","x1","x2","x3","x4"]) 
    print(text)
sampleA=np.array(input("Mời bạn nhập dòng để test random forest: ").split()).reshape(1,-1)
sampleB = pd.DataFrame(data=sampleA, columns=["x0","x1","x2","x3","x4"])
predictions=[]
for tree in save:
    prediction=tree.predict(sampleB)
    predictions.append(prediction) 
majority_vote = mode(predictions,keepdims=True)[0][0]
print(f"Kết quả bỏ phiếu lấy số đông là: {majority_vote}")
print(predictions)
##3.9 6.1 5.9 5.5 5.9
