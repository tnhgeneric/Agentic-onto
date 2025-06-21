from fastapi import FastAPI, Body
from langchain_google_vertexai import ChatVertexAI

app = FastAPI()

llm = ChatVertexAI(
    model="gemini-2.5-pro",  # latest Gemini model
    temperature=0.2,
    max_output_tokens=512,
    location="us-central1"
)

@app.post("/ask")
async def ask(prompt: str = Body(..., embed=True)):
    response = llm.invoke(prompt)
    return {"response": response.content}
