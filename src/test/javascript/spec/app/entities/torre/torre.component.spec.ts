/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TorreComponent from '@/entities/torre/torre.vue';
import TorreClass from '@/entities/torre/torre.component';
import TorreService from '@/entities/torre/torre.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Torre Management Component', () => {
    let wrapper: Wrapper<TorreClass>;
    let comp: TorreClass;
    let torreServiceStub: SinonStubbedInstance<TorreService>;

    beforeEach(() => {
      torreServiceStub = sinon.createStubInstance<TorreService>(TorreService);
      torreServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TorreClass>(TorreComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          torreService: () => torreServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      torreServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTorres();
      await comp.$nextTick();

      // THEN
      expect(torreServiceStub.retrieve.called).toBeTruthy();
      expect(comp.torres[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      torreServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTorre();
      await comp.$nextTick();

      // THEN
      expect(torreServiceStub.delete.called).toBeTruthy();
      expect(torreServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
