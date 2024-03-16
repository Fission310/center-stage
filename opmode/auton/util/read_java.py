import csv 
import sys

in_file = sys.argv[1]
out_file = sys.argv[2]

# Constants File
f = open(in_file, 'r')
const_line = f.readlines()
f.close()

var_dict = {}

directions = {"LEFT": 180, "UP": 90, "RIGHT": 0, "DOWN": 270}

#Reading Constants
i = 0
while i < len(const_line):
  line = const_line[i]
  if "new Constant" in line:
    name = line[8:line.index(" =")]
    pos = ["0"] * 9
    # Dictionary of Constants: {Pos Name: Pos Value}
    for j in range(len(pos)):
      sign = ","
      if j == len(pos) - 1:
        sign = ");"
      new_line = const_line[i + 1].strip().replace(sign, "")
      for d in directions:
        if d in new_line:
          new_line = str(directions[d])
      if "Radians" in new_line:
        new_line = new_line[new_line.index("(")+1:new_line.index(")")]
      pos[j] = new_line
      i += 1

    var_dict[name] = pos
  i += 1

with open(out_file, 'w', newline='') as csv_file:
  first_row = ["OFFSETS",
               "LEFT_X", 
               "LEFT_Y",
               "LEFT_HEADING",
               "CENTER_X",
               "CENTER_Y",
               "CENTER_HEADING", 
               "RIGHT_X",
               "RIGHT_Y", 
               "RIGHT_HEADING"]
  writer = csv.writer(csv_file)
  writer.writerow(first_row)
  for var in var_dict:
    writer.writerow([var] + var_dict[var])
