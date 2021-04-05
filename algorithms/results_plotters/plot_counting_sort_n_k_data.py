import pandas as pd
import matplotlib.pyplot as plt

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

def get_color(series):
    colors = {}
    colors['k=100'] = 'k'
    colors['k=1000'] = 'r'
    colors['k=10000'] = 'b'
    colors['k=100000'] = 'm'
    colors['k=1000000'] = 'g'
    colors['k=10000000'] = 'c'
    colors['merge sort'] = '0.75'
    colors['quick sort'] = '0.50'
    return colors[series]

def plot_counting_sort_n_k_data_log_log(series, data):
    print("plot_counting_sort_n_k_data_log_log()")
    fig, ax1 = create_figure(r'$n$', ylabel='CPU Time / milliseconds', title='')
    ax1.set_xscale('log')
    ax1.set_yscale('log')
    for k in series:
        print ("\t-plotting data for:", k)
        linestyle = '-'
        if k == 'merge sort':
            linestyle = '--'
        if k == 'quick sort':
            linestyle = '--'
        ax1.plot(data['arraySize'], data[k], marker='s', linestyle=linestyle, color=get_color(k), label=k)
    ax1.legend()
    plt.savefig('counting_sort_n_k_data_log_log.png', bbox_inches='tight', format='png', dpi=600)
    plt.close(fig)
    fig.clf()  # Clear figure
    plt.close('all')  # Close a figure window
    plt.close(plt.gcf())

if __name__ == '__main__':
    print("main()")

    data = pd.read_csv("plot_counting_sort_n_k_data.dat", sep='\t+')
    plot_counting_sort_n_k_data_log_log(series=['k=100', 'k=1000', 'k=10000', 'k=100000', 'k=1000000', 'k=10000000', 'merge sort', 'quick sort'], data=data)