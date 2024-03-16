import csv 

file_name = "test"

# Constants File
f = open(file_name + '.java', 'r')
const_line = f.readlines()
f.close()

var_dict = {"SPIKE": [], 
            "STACK_1": [],
            "STACK_2": [],
            "STACK_3": [],
            "TRUSS_1": [],
            "TRUSS_2": [], 
            "TRUSS_3": [],
            "TAG_1": [],
            "TAG_2": [], 
            "TAG_3": [], 
            "PARK": []}
var_dict_names = list(var_dict.keys())
curr_var = 0

#Reading Constants
for i in range(len(const_line)):
  line = const_line[i]
  if "new Constant" in line:
    pos = ["0", "0", "0", "0", "0" , "0", "0", "0", "0"]
    # Dictionary of Constants: {Pos Name: Pos Value}
    for j in range(len(pos)):
      sign = ","
      if j == len(pos) - 1:
        sign = ");"
      new_line = const_line[i + 1].strip().replace(sign, "")
      if "Radians" in new_line:
        new_line = new_line[new_line.index("(")+1:new_line.index(")")]
      pos[j] = new_line
      i += 1

    var_dict[var_dict_names[curr_var]] = pos
    curr_var += 1

with open(file_name + '.csv', 'w', newline='') as csv_file:
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

  