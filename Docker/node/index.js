const express = require('express')
const mysql = require('mysql2')

const app = express()
const port = 3000

const config = {
  host: 'db',
  user: 'root',
  password: '123',
  database: 'nodedb'
}

const connection = mysql.createConnection(config)

app.get('/', (req, res) => {
  res.send('<h1>Hello World</h1>')
})

app.listen(port, () => {
  console.log('Rodando na porta ' + port)
})

const sql = `INSERT INTO peoples(name, age) VALUES ('Matheus', 19)`

connection.query(sql)

connection.end()