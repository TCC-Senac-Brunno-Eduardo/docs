
Exemplo de um marcador de aglomeração
```typescript
{
    "id": string,
    "latitude": number,
    "longitude": number,
    "city": string,
    "title": string,
    "description": string
}
```

Exemplo de uma mensagem na fila ao inserir novo marcador
```typescript
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
```


Exemplo de uma mensagem na fila ao deletar um marcador

```typescript
{
  "message": "DELETED_MARKER",
  "city": [
    {
      city: string,
      markersId: [ string ]
    }
  ]
} 
```
    