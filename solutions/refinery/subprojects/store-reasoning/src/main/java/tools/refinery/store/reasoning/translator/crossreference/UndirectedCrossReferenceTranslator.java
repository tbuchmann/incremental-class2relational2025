/*
 * SPDX-FileCopyrightText: 2023 The Refinery Authors <https://refinery.tools/>
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package tools.refinery.store.reasoning.translator.crossreference;

import tools.refinery.store.dse.transition.Rule;
import tools.refinery.store.model.ModelStoreBuilder;
import tools.refinery.store.model.ModelStoreConfiguration;
import tools.refinery.store.query.dnf.Query;
import tools.refinery.store.query.view.ForbiddenView;
import tools.refinery.store.reasoning.lifting.DnfLifter;
import tools.refinery.store.reasoning.literal.Concreteness;
import tools.refinery.store.reasoning.literal.Modality;
import tools.refinery.store.reasoning.refinement.RefinementBasedInitializer;
import tools.refinery.store.reasoning.representation.PartialRelation;
import tools.refinery.store.reasoning.translator.PartialRelationTranslator;
import tools.refinery.store.reasoning.translator.multiplicity.InvalidMultiplicityErrorTranslator;
import tools.refinery.store.representation.Symbol;
import tools.refinery.store.representation.TruthValue;

import static tools.refinery.store.query.literal.Literals.not;
import static tools.refinery.store.reasoning.actions.PartialActionLiterals.add;
import static tools.refinery.store.reasoning.literal.PartialLiterals.*;
import static tools.refinery.store.reasoning.translator.multiobject.MultiObjectTranslator.MULTI_VIEW;

public class UndirectedCrossReferenceTranslator implements ModelStoreConfiguration {
	private final PartialRelation linkType;
	private final UndirectedCrossReferenceInfo info;
	private final Symbol <TruthValue> symbol;

	public UndirectedCrossReferenceTranslator(PartialRelation linkType, UndirectedCrossReferenceInfo info) {
		this.linkType = linkType;
		this.info = info;
		symbol = Symbol.of(linkType.name(), 2, TruthValue.class, TruthValue.UNKNOWN);
	}

	@Override
	public void apply(ModelStoreBuilder storeBuilder) {
		var name = linkType.name();
		var type = info.type();
		var forbiddenView = new ForbiddenView(symbol);
		var mayName = DnfLifter.decorateName(name, Modality.MAY, Concreteness.PARTIAL);

		var mayNewSource = CrossReferenceUtils.createMayHelper(linkType, type, info.multiplicity(), false);

		storeBuilder.with(PartialRelationTranslator.of(linkType)
				.symbol(symbol)
				.may(Query.of(mayName, (builder, source, target) -> {
					builder.clause(
							mayNewSource.call(source),
							mayNewSource.call(target),
							not(forbiddenView.call(source, target))
					);
					if (info.isConstrained()) {
						// Violation of monotonicity:
						// Edges violating upper multiplicity will not be marked as {@code ERROR}, but the
						// corresponding error pattern will already mark the node as invalid.
						builder.clause(
								must(linkType.call(source, target)),
								not(forbiddenView.call(source, target)),
								may(type.call(source)),
								may(type.call(target))
						);
					}
				}))
				.refiner(UndirectedCrossReferenceRefiner.of(symbol, type))
				.initializer(new RefinementBasedInitializer<>(linkType))
				.decision(Rule.of(linkType.name(), (builder, source, target) -> builder
						.clause(
								may(linkType.call(source, target)),
								not(candidateMust(linkType.call(source, target))),
								not(MULTI_VIEW.call(source)),
								not(MULTI_VIEW.call(target))
						)
						.action(
								add(linkType, source, target)
						))));

		storeBuilder.with(new InvalidMultiplicityErrorTranslator(type, linkType, false, info.multiplicity()));
	}
}
