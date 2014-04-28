import numpy as np
from scipy import *
from scipy.cluster.vq import vq, kmeans
import scipy.linalg as linalg
import networkx as nx
import matplotlib.pyplot as plt

std_clusterA
std_clusterB


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
	tp = comb(len(clusterA_diff),2),+comb(len(clusterB_diff),2)+comb(len(clusterA_same),2)+comb(len(clusterB_same),2)
	fp = (len(clusterA)-len(clusterA_same))*len(clusterA_same)+(len(clusterB)-len(clusterB_same))*len(clusterB_same)
	fn = (len(clusterA)-len(clusterA_same))*len(clusterA_diff)+(len(clusterB)-len(clusterB_same))*len(clusterB_diff)
	tn = len(clusterA_diff)*len(clusterB_same)+len(clusterB_diff)*len(clusterA_same)
	p=(float) tp/(tp+fp)
	r = (float)(tp/(tp+fn)
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

def getAdjMatrix(m):
	d=[np.sum(row) for row in m]
	D=np.diag(d)
	L=D-w
	return L

graph = nx.read_gml("karate.gml")

k_matrix=nx.to_numpy_matrix(karate_graph)

adj_matrix = getAdjMatrix(k_matrix))
u,s,v = np.linalg.svd(adj_matrix)

# generating a diagonal matrix with diag_deg
diag_deg, _ = np.histogram(k_matrix.nonzero()[0], np.arange(k_matrix.shape[0]+1))
dim = k_matrix.shape[0]
diag_mat = np.zeros((dim**2, ))
diag_mat[np.arange(0, dim**2, dim+1)] = diag_deg
diag_mat.reshape((dim, dim))



