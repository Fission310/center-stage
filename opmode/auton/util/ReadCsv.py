import csv

file_name = 'test'

var_dict = {}

with open(file_name + '.csv', 'r') as csv_file:
  csv_reader = csv.reader(csv_file)
  curr_row = csv_reader.__next__()

  # Dictionary of Constants: {Pos Name: Pos Value}
  pos = {}
  for i in range(1, len(curr_row)):
    pos[curr_row[i]] = 0

  for row in csv_reader:
    var_dict[row[0]] = pos.copy()
    for i in range(1, len(row)):
      var_dict[row[0]][list(pos)[i - 1]] = row[i]

# Constants File
f = open(file_name + '.java', 'r')
const_file = f.read()
f.close()

# Heading
heading = const_file[:const_file.index("setConstants() {") +
                     len("setConstants() {")]

#Setting Constants
template = '''
        VAR_NAME = new Constant(
                LEFT_X,
                LEFT_Y,
                LEFT_HEADING,
                CENTER_X,
                CENTER_Y,
                CENTER_HEADING,
                RIGHT_X,
                RIGHT_Y,
                RIGHT_HEADING);
    '''

append = template

#Creating java file
for var in var_dict:
  append = append.replace('VAR_NAME', var)
  for pos in var_dict[var]:
    if "HEADING" in pos:
      head = 'Math.toRadians(' + var_dict[var][pos] + ')'
      append = append.replace(pos, head)
      continue
    append = append.replace(pos, var_dict[var][pos])

  heading += append
  append = template

heading += '''}
}'''

#Replacing java file with updated one
f = open(file_name + '.java', 'w')
f.write(heading)
f.close()