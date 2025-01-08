function getUrlParams() {
    const params = new URLSearchParams(window.location.search);
    return {
        rows: parseInt(params.get('rows')) || 5, // PAR DEFAULT 5 LIGNES
        columns: parseInt(params.get('columns')) || 5, // PAR DEFAULT 5 COLONNES
        colors: params.get('colors') || '' // PAR DEFAULT AUCUNE COULEUR
    };
}

function createGrid() {
    const params = getUrlParams();
    const grid = document.getElementById('grid');
    
    grid.style.gridTemplateColumns = `repeat(${params.columns}, 60px)`;
    grid.style.gridTemplateRows = `repeat(${params.rows}, 60px)`;

    let colorIndex = 0;
    for (let i = 0; i < params.rows * params.columns; i++) {
        const cell = document.createElement('div');
        cell.className = 'cell ' + getColorClass(params.colors[colorIndex]);
        grid.appendChild(cell);
        
        colorIndex++;
    }
}

// LIEN ENTRE LE CODE COULEUR ET LA CLASSE CSS
function getColorClass(code) {
    switch (code) {
        case 'r': return 'red';
        case 'b': return 'blue';
        case 'a': return 'assassin';
        case 'n': return 'neutral';
        default: return 'neutral';
    }
}

window.onload = createGrid;
