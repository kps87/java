import glob
import os

import pandas as pd
import matplotlib.pyplot as plt

def get_dat_files(dir=None):
    print("get_dat_files() dir = ", dir)
    return glob.glob(dir + "/*.dat")


def read_dat_files(files):
    print("read_dat_files()")
    dataframes = {}
    for f in files:
        property = os.path.splitext(os.path.split(f)[1])[0]
        dataframes[property] = pd.read_csv(f, sep='\s+')
    return dataframes

def create_figure(xlabel='nan', ylabel='nan', title=''):
    """
    create a figure with labels
    """
    fig, ax1 = plt.subplots()
    ax1.set_ylabel(ylabel)
    ax1.set_xlabel(xlabel)
    plt.title(title)
    plt.tight_layout()

    return fig, ax1

def get_algorithm_color(algorithm):
    colors = {}
    colors['bubble'] = 'k'
    colors['insertion'] = 'r'
    colors['selection'] = 'b'
    colors['quick'] = 'm'
    colors['counting'] = 'g'
    colors['merge'] = 'c'
    return colors[algorithm]

def plot_summary_data_log_log(algorithms, dataframes):
    print("plot_summary_data_log_log()")
    fig, ax1 = create_figure(r'$n$', ylabel='CPU Time / milliseconds', title='')
    ax1.set_xscale('log')
    ax1.set_yscale('log')
    for alg in algorithms:

        print ("\t-plotting data for:", alg, "sort")
        x = dataframes['meanRunTimes']['arraySize']
        mean_cpu_times = dataframes['meanRunTimes'][alg]
        best_cpu_times = dataframes['bestRunTimes'][alg]
        worst_cpu_times = dataframes['worstRunTimes'][alg]
        sigma_cpu_times = dataframes['sigmaRunTimes'][alg]

        ax1.plot(x, mean_cpu_times, marker='s', color=get_algorithm_color(alg), label=alg + " sort")
        ax1.fill_between(x, best_cpu_times, worst_cpu_times, color=get_algorithm_color(alg), alpha=0.4)

    ax1.legend()
    plt.savefig('benchmark_times_vs_nsamples_log_log.png', bbox_inches='tight', format='png', dpi=600)
    plt.close(fig)
    fig.clf()  # Clear figure
    plt.close('all')  # Close a figure window
    plt.close(plt.gcf())

def plot_summary_data(algorithms, dataframes):
    print("plot_summary_data()")
    fig, ax1 = create_figure(r'$n$', ylabel='CPU Time / milliseconds', title='')

    for alg in algorithms:

        print ("\t-plotting data for:", alg, "sort")
        x = dataframes['meanRunTimes']['arraySize']
        mean_cpu_times = dataframes['meanRunTimes'][alg]
        best_cpu_times = dataframes['bestRunTimes'][alg]
        worst_cpu_times = dataframes['worstRunTimes'][alg]
        sigma_cpu_times = dataframes['sigmaRunTimes'][alg]

        ax1.plot(x, mean_cpu_times, marker='s', color=get_algorithm_color(alg), label=alg + " sort")
        ax1.fill_between(x, best_cpu_times, worst_cpu_times, color=get_algorithm_color(alg), alpha=0.4)

    ax1.legend()
    plt.savefig('benchmark_times_vs_nsamples.png', bbox_inches='tight', format='png', dpi=600)
    plt.close(fig)
    fig.clf()  # Clear figure
    plt.close('all')  # Close a figure window
    plt.close(plt.gcf())

if __name__ == '__main__':
    print("main()")
    algorithms = ['bubble', 'selection', 'insertion', 'merge', 'quick', 'counting']

    # get and plot the summary data
    dat_files = get_dat_files(dir=os.path.join(os.getcwd(), 'summary_data_final'))
    dataframes = read_dat_files(files=dat_files)
    plot_summary_data(algorithms, dataframes)
    plot_summary_data_log_log(algorithms, dataframes)
