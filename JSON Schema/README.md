
Exemplo de um marcador de aglomeração
```json
{
    "id": "string",
    "latitude": "number",
    "longitude": "number",
    "city": "string",
    "title": "string",
    "description": "string"
}
```


Exemplo de uma mensagem na fila ao inserir novo marcador
```json
{
  "message": "NEW_MARKER",
  "report": {
    "id": "string",
    "latitude": "number",
    "longitude": "number",
    "city": "string",
    "title": "string",
    "description": "string"
  },
} 
```


Exemplo de uma mensagem na fila ao deletar um marcador

```json
{
  "message": "DELETED_MARKER",
  "city": [
    {
      "city": "string",
      "markersId": [ "string" ]
    }
  ]
} 
```
    