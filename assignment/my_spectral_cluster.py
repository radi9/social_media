#all necessary package, numpy,scipy,networkx,matplotlib
#it's free to run,if you install all of the packages on python
import numpy as np
from scipy import *
from scipy.cluster.vq import vq, kmeans
import scipy.linalg as linalg
import networkx as nx
import matplotlib.pyplot as plt


std_clusterA = [1,2,3,4,5,6,7,8,11,12,13,14,17,18,20,22]
std_clusterB = [9,10,15,16,19,21,23,24,25,26,27,28,29,30,31,32,33,34]

def comb(x,y):
	temp=1
	tempII=1
	tempIII=1
	for i in range(1,x):
		temp*=i
	temp*=x
	for i in range(1,y):
		tempII*=i
	tempII*=y
	for i in range(1,x-y):
		tempIII*=i
	tempIII*=(x-y)
	return temp/(tempII*tempIII)

def f_measure(clusterA,clusterB):
	clusterA_diff = getDifference(clusterA,std_clusterA)
	clusterB_diff = getDifference(clusterB,std_clusterB)
	clusterA_same = getSame(clusterA,std_clusterA)
	clusterB_same = getSame(clusterB,std_clusterB)
	tp = comb(len(clusterA_diff),2)+comb(len(clusterB_diff),2)+comb(len(clusterA_same),2)+comb(len(clusterB_same),2)+0.0
	fp = (len(clusterA)-len(clusterA_same))*len(clusterA_same)+(len(clusterB)-len(clusterB_same))*len(clusterB_same)+0.0
	fn = (len(clusterA)-len(clusterA_same))*len(clusterA_diff)+(len(clusterB)-len(clusterB_same))*len(clusterB_diff)+0.0
	tn = len(clusterA_diff)*len(clusterB_same)+len(clusterB_diff)*len(clusterA_same)+0.0
	p = tp/(tp+fp)
	r = tp/(tp+fn)
	print (2*(p*r)/(p+r))

def getDifference(arr_A,arr_B):
	#in array A but not in B
	temp = []
	for element in arr_A:
		if element not in arr_B:
			temp.append(element)
	return temp

def getSame(arr_A,arr_B):
	#in A and also in B
	temp = []
	for element in arr_A:
		if element in arr_B:
			temp.append(element)
	return temp

def getDegreeVec(adj_matirx):
	diag_deg,_ = np.histogram(adj_matirx.nonzero()[0], np.arange(adj_matirx.shape[0]+1))
	return diag_deg 

def getDiag(m):
	d = [np.sum(row) for row in m]
	diag = np.diag(d)
	return diag

def getModularity(m):
	degree_vec = getDegreeVec(m)
	indot = sum(p*q for p,q in zip(degree_vec, degree_vec))
	diag = getDiag(m)
	total_degree = diag.trace()
	return (m - float(indot/total_degree))

def getNormLaplacian(m):
	#get the Unnormalized Laplacian matrix
	d = [np.sum(row) for row in m]
	diag = np.diag(d)
	unNor_lap=diag-m

	#normalized Laplacian then
	#D = D^(-1/2)
	D = np.power(np.linalg.matrix_power(diag,-1),0.5)
	n_lap=np.dot(np.dot(D,unNor_lap),D)
	return n_lap

def getK_smallestEigvec(laplac,k):
	#this function for get the first k small eigenvalue 
	#and relate to the eigenvector
	eigval,eigvec = linalg.eig(laplac)
	dim = len(eigval)

	#get the first k smallest eigenvalue
	eigval_array = dict(zip(eigval,range(0,dim)))
	sort_eig = np.sort(eigval)[0:k]
	index = [eigval_array[k] for k in sort_eig]
	return eigval[index],eigvec[:,index]
	
	

ka_graph = nx.read_gml("karate.gml")

adj_Matrix = nx.to_numpy_matrix(ka_graph)

laplac = getNormLaplacian(adj_Matrix)

eigVals,eigVecs=getK_smallestEigvec(laplac,2)

clusterB = [i for i in range(0,34) if eigVecs[i,1]>0]

clusterA = [i for i in range(0,34) if eigVecs[i,1]<0]

print clusterA[::]
print clusterB[::]

f_measure(clusterA,clusterB)


modularity = getModularity(adj_Matrix)

eigVals,eigVecs=getK_smallestEigvec(modularity,2)

b_clusterB = [i for i in range(0,34) if eigVecs[i,1]>0] 
b_clusterA = [i for i in range(0,34) if eigVecs[i,1]<0]

print b_clusterA
print b_clusterB

f_measure(b_clusterA,b_clusterB)
