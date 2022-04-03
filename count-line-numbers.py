import os
rootdir = 'src/'

lines = 0
file_count = 0

for subdir, _, files in os.walk(rootdir):
    for file in files:
        f = os.path.join(subdir, file)
        if f.endswith('.java'):
            file_count += 1
            with open(f, 'rt') as file_open:
                lines += len(file_open.readlines())


print(f'{lines} total across {file_count} files')