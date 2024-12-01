const renderTableRow = (name, results, lastUpdated, index) => {
  const tableBody = document.getElementById('body');
  const row = document.createElement('tr');
  const thead = document.createElement('th');
  thead.setAttribute('scope', 'row'); // Add scope attribute
  row.appendChild(thead);
  thead.textContent = name;
  results.forEach(result=>{
    const tbody = document.createElement('th');
    tbody.textContent = result
    switch(result){
      case 'W':
        tbody.setAttribute('class', 'text-success');
        break;
      case 'L':
        tbody.setAttribute('class', 'text-danger');
        break;
      default:
        tbody.setAttribute('class', 'text-warning');
    }
    row.appendChild(tbody);
    tableBody.append(row);
  });
  const tbody = document.createElement('td');
  tbody.textContent = lastUpdated;
  row.appendChild(tbody);
  // index % 2 == 0? row.setAttribute('class','table-primary') : row.setAttribute('class','table-warning');
}

// Function to create the grid based on results
const createPlayerResultsGrid = (name, results, divID) => {
    const gridContainer = document.getElementById(divID);
    const studentLabel = document.createElement('div');
    studentLabel.classList.add('grid-item');
    studentLabel.textContent = name;
    gridContainer.appendChild(studentLabel);
    results.forEach((result) => {
        const gridItem = document.createElement('div');
        gridItem.classList.add('grid-item', result);
        gridItem.textContent = result;
        gridContainer.appendChild(gridItem);
    });
}
const createBotResultsGrid = (lastUpdate, results, divID) => {
    const gridContainer = document.getElementById(divID);
    results.forEach((result) => {
        const gridItem = document.createElement('div');
        gridItem.classList.add('grid-item', result);
        gridItem.textContent = result;
        gridContainer.appendChild(gridItem);
    });
    const lastUpdateDiv = document.createElement('div');
    lastUpdateDiv.classList.add('grid-item');
    lastUpdateDiv.textContent = lastUpdate;
    gridContainer.appendChild(lastUpdateDiv);
}

const fetchData = async () => {
    try {
      const response = await fetch("../backend/fetchData.php");
      if (!response.ok) {
        throw new Error("Failed to fetch");
      }
      const results = await response.json();
      return results;
    } catch (error) {
      console.error("Error:", error);
    }
  };

// Dummy data for the chart
const initializeTable = async() => {
    const studentData = await fetchData();
    console.log(studentData);
    studentData.forEach((student,index) => {
      const studentResults = (student.botFirstEMH + student.playerFirstEMH).split("");
      renderTableRow(student.publicName, studentResults, student.lastDate, index);
        // const botFirstResults = student.botFirstEMH.split("");
        // const playerFirstResults = student.playerFirstEMH.split("");
        // createBotResultsGrid(student.lastDate, botFirstResults,"botFirst");
        // createPlayerResultsGrid(student.publicName, playerFirstResults,"playerFirst");
    });
}

initializeTable();




