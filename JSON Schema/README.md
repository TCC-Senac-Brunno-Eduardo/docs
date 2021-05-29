
Exemplo de um marcador de aglomeração

{
    "id": string,
    "latitude": number,
    "longitude": number,
    "city": string,
    "title": string,
    "description": string
}

Exemplo de uma mensagem na fila ao inserir novo marcador

{
  "message": "NEW_MARKER",
  "report": {
    "id": string,
    "latitude": number,
    "longitude": number,
    "city": string,
    "title": string,
    "description": string
  },
} 

Exemplo de uma mensagem na fila ao deletar um marcador

{
  "message": "DELETED_MARKER",
  "city": [
    {
      city: string,
      markersId: [ string ]
    }
  ]
} 
    