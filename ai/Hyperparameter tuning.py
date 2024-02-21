from sklearn.model_selection import GridSearchCV
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from transformers import BertTokenizer, BertModel
import torch
import numpy as np

# Assuming you have your own dataset (texts, labels)
# Replace the following lines with your dataset loading
df = pd.read_csv("/content/merged_data.csv")  
labels = df['label'].values
texts = df['transcript'].values

# Tokenize the texts using Kobert tokenizer
tokenizer = BertTokenizer.from_pretrained('monologg/kobert')
tokenized_texts = [tokenizer(text, return_tensors='pt', padding=True, truncation=True, max_length=128) for text in texts]

# Convert BERT embeddings to numpy arrays
bert_model = BertModel.from_pretrained('monologg/kobert')
embeddings = [bert_model(**text).last_hidden_state.mean(dim=1).detach().numpy() for text in tokenized_texts]
X = np.vstack(embeddings)

# Split the dataset into training and test sets
X_train, X_test, y_train, y_test = train_test_split(X, labels, test_size=0.2, random_state=42)

# Initialize your model
model = SVC()

# Define the hyperparameter grid
param_grid = {
    'C': [0.1, 1, 10, 100],
    'kernel': ['linear', 'rbf', 'poly'],
    'gamma': [0.01, 0.1, 1, 'scale']
}

# Perform grid search
grid_search = GridSearchCV(model, param_grid, cv=5)
grid_search.fit(X_train, y_train)

# Print the best hyperparameters
print("Best Hyperparameters:", grid_search.best_params_)

# Evaluate the model on the test set
y_pred = grid_search.predict(X_test)
accuracy = accuracy_score(y_test, y_pred)
print(f"Test Accuracy: {accuracy}")
