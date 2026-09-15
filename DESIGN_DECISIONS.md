\# Design Decisions \& Tradeoffs



This document records the major engineering decisions made while building

the LIFE Event Intelligence module and explains why those decisions were made.



\---



\## 1. Rule-Based NLP Instead of a Mandatory LLM



\*\*Decision:\*\*  

Use local regex and keyword-based processing for the core pipeline instead of

making an external LLM a required dependency.



\*\*Why:\*\*



\- No per-request API cost.

\- No network dependency.

\- Lower latency.

\- Message content remains within the local system.

\- Every classification can be explained through explicit rules and evidence.

\- The system continues working when an external service is unavailable.

\- The approach is suitable for an offline-friendly hackathon architecture.



\*\*Tradeoff accepted:\*\*



A rule-based system has weaker generalization than an LLM for completely

unseen wording, synonyms, and semantic relationships.



\*\*Future enhancement:\*\*



Possible future improvements include fuzzy matching, offline embeddings, or an

optional LLM fallback for messages that cannot be classified locally.



\---



\## 2. Configurable Confidence Weights Instead of a Trained Model



\*\*Decision:\*\*  

Calculate ownership confidence using configurable evidence weights rather

than using a trained probabilistic machine-learning model.



\*\*Why:\*\*



The evidence signals currently used by the system are mostly explicit and

boolean-shaped. For example, a sender may be confirmed or unconfirmed.



An additive scoring model is therefore:



\- Simple.

\- Deterministic.

\- Easy to test.

\- Easy to explain.

\- Easy to modify through configuration.



The final score is clamped to the range `0.0` to `1.0`.



\*\*Tradeoff accepted:\*\*



This approach is less statistically rigorous than a trained classifier.



It is appropriate for the current scope because the number of evidence

signals is limited and explainability is a major design requirement.



\---



\## 3. Ownership Evidence Based on Sender Confirmation



\*\*Decision:\*\*  

Ownership confirmation is associated with the specific sender rather than

being treated as universal confirmation of an event type.



\*\*Why:\*\*



Confirming one electricity bill from one sender should not automatically make

every electricity bill from every sender belong to the user.



This prevents confirmation from one context from incorrectly spreading to

unrelated messages.



\*\*Tradeoff accepted:\*\*



The system may require additional feedback before confidence increases for a

new sender.



This is intentional because conservative ownership inference is safer than

assuming ownership.



\---



\## 4. Fingerprint-Based Duplicate Detection



\*\*Decision:\*\*  

Detect duplicates using structured event fields rather than relying only on

raw text similarity.



The fingerprint is based on structured information such as:



\- Event type

\- Amount

\- Due date

\- Sender

\- Reference number



\*\*Why:\*\*



Two messages can describe the same event using completely different wording.



For example:



```text

Your electricity bill is due tomorrow.

