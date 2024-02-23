
# Load model and tokenizer
model_name = "chaeyeon1/vp_kobert_model"
model = AutoModelForSequenceClassification.from_pretrained(model_name)
tokenizer = AutoTokenizer.from_pretrained(model_name)
