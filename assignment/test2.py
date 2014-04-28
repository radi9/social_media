import numpy as np
import scipy as sp
import scipy.linalg as linalg
import networkx as nx

def getNormLaplacian(W):
	d=[np.sum(row) for row in W]
	D=np.diag(d)
	L=D-W
	#Dn=D^(-1/2)
	Dn=np.power(np.linalg.matrix_power(D,-1),0.5)
	Lbar=np.dot(np.dot(Dn,L),Dn)
	return Lbar

def getKSmallestEigVec(Lbar,k):
	eigval,eigvec=linalg.eig(Lbar)
	dim=len(eigval)

	#find the first k small eigenvalue
	dictEigval=dict(zip(eigval,range(0,dim)))
	kEig=np.sort(eigval)[0:k]
	ix=[dictEigval[k] for k in kEig]
	return eigval[ix],eigvec[:,ix]

def checkResult(Lbar,eigvec,eigval,k):
	check=[np.dot(Lbar,eigvec[:,i])-eigval[i]*eigvec[:,i] for i in range(0,k)]
	length=[np.linalg.norm(e) for e in check]/np.spacing(1)
	print("Lbar*v-lamda*v are %s*%s" % (length,np.spacing(1)))

g=nx.read_gml("karate.gml")
nodeNum=len(g.nodes())
m=nx.to_numpy_matrix(g)
Lbar=getNormLaplacian(m)
k=2
kEigVal,kEigVec=getKSmallestEigVec(Lbar,k)
#print("k eig val are %s" % kEigVal)
#print("k eig vec are %s" % kEigVec)
checkResult(Lbar,kEigVec,kEigVal,k)

#maximum modularity

clusterA=[i for i in range(0,nodeNum) if kEigVec[i,1]>0]
clusterB=[i for i in range(0,nodeNum) if kEigVec[i,1]<0]

#draw graph
colList=dict.fromkeys(g.nodes())
for node,score in colList.items():
	if node in clusterA:
		colList[node]=0
	else:
		colList[node]=0.6


print clusterA[::]
print clusterB[::]

