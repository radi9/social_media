import numpy as np
from scipy import *
from scipy.cluster.vq import vq, kmeans
import scipy.linalg as linalg
import networkx as nx
import matplotlib.pyplot as plt

std_clusterA=[1,2,3,4,5,6,7,8,11,12,13,14,17,18,20,22]
std_clusterB=[9,10,15,16,19,21,23,24,25,26,27,28,29,30,31,32,33,34]

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
	

karate_graph = nx.read_gml("karate.gml")

karate_adjance=nx.to_numpy_matrix(karate_graph)

k = kmeans(karate_adjance,2)

clusterA=[i for i in range(0,len(k[0][0])) if k[0][0][i]>k[0][1][i]]

clusterB=[i for i in range(0,len(k[0][1])) if k[0][1][i]>k[0][0][i]]


clusterA.sort()
clusterB.sort()

print clusterA[::]
print clusterB[::]

print std_clusterA[::]
print std_clusterB[::]

def getAdjMatrix(Matrix):
	

f_measure(clusterA,clusterB)
