The following is a description of the contents of each directory:

	report.pdf					- 	the main project report
	
	/src					-	contains the source code for each of java package
	/bin					-	contains pre-compiled java src code (jdk11)
	/doc 					-	contains documentation on source code generated via JavaDoc

	/latex/						-	contains latex source used to generate the final report

	/results_plotters			-	contains tabulated data and python scripts to plot
									results of java benchmarking application
		/iteration_data_final	-	contains CPU times as a function of iteration number for each algorithm
									CPU times are reported in nanoseconds
		/summary_data_final		-	contains CPU times averaged over 10 iterations for each algorithm
									worst, average and best case run times, along with standard deviations and coefficients
									of variation are reported in separate files
									CPU times are reported in milliseconds
