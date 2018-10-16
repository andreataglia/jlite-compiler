import pytest, os, subprocess

files_to_test = []
for dir in os.listdir('.'):
    if dir[0] == '.' or not os.path.isdir(dir):
        continue
    for file in os.listdir('./{}'.format(dir)):
        files_to_test.append(('./{}/{}'.format(dir, file), dir == 'pass'))


@pytest.mark.parametrize("filename,should_pass", files_to_test)
def test_eval(filename, should_pass):
    p = subprocess.Popen(['java', '-cp', '/usr/share/java/cup/java-cup-11b.jar:..', 'Main', filename],
                    stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = p.communicate()
    assert ('Exception' in stderr.decode('utf-8')) != should_pass,\
        "File {} did not have the correct result".format(filename)
