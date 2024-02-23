from fastapi import FastAPI, HTTPException, Request
from pydantic import BaseModel
from transformers import AutoModelForSequenceClassification, AutoTokenizer
import torch
from fastapi.responses import JSONResponse


app = FastAPI()


# Load model and tokenizer
model_name = "chaeyeon1/vp_kobert_model"
model = AutoModelForSequenceClassification.from_pretrained(model_name)
tokenizer = AutoTokenizer.from_pretrained(model_name)

class PredictInput(BaseModel):
    text: str  # Input text

class PredictOutput(BaseModel):
    is_phishing: bool  # Whether it's a phishing or not

@app.post("/predict", response_model=PredictOutput)
async def predict(payload: PredictInput):
    try:
        # Tokenize the text and pass it to the model as input
        inputs = tokenizer(payload.text, return_tensors='pt', padding=True, truncation=True)
        with torch.no_grad():
            outputs = model(**inputs)

        logits = outputs.logits
        probabilities = torch.nn.functional.softmax(logits, dim=1).squeeze().tolist()

        # Determine whether it's phishing or not based on probabilities
        is_phishing = probabilities[1] > 0.5  # True if the probability of phishing class is greater than 0.5

        return {"is_phishing": is_phishing}

    except Exception as e:
        print(f"Error occurred: {str(e)}")
        raise HTTPException(status_code=500, detail=f"Internal Server Error: {str(e)}")

@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return JSONResponse(
        status_code=exc.status_code,
        content={"error": exc.detail}
    )
